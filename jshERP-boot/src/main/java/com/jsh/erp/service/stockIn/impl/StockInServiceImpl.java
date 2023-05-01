package com.jsh.erp.service.stockIn.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockInBill;
import com.jsh.erp.datasource.mappers.StockInBillMapper;
import com.jsh.erp.datasource.mappers.StockInMapper;
import com.jsh.erp.datasource.vo.StockInBillVo;
import com.jsh.erp.datasource.vo.StockInDetailVo;
import com.jsh.erp.datasource.vo.StockInTotal;
import com.jsh.erp.datasource.vo.StockInVo;
import com.jsh.erp.service.stockIn.StockInService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockInServiceImpl extends ServiceImpl<StockInMapper, StockIn> implements StockInService {

    @Autowired
    private StockInMapper stockInMapper;
    @Autowired
    private StockInBillMapper stockInBillMapper;

    @Override
    public boolean upload(List<StockInVo> stockInVoList) {
        List<StockIn> stockInList = new ArrayList<>();
        for (StockInVo stockInVo : stockInVoList) {
            StockIn stockIn = new StockIn();
            BeanUtils.copyProperties(stockInVo, stockIn);
            stockIn.setStatus(BusinessConstants.STOCK_IN_STATUS);
            stockIn.setCreateTime(LocalDateTime.now());
            stockInList.add(stockIn);
        }
        return this.saveBatch(stockInList);
    }

    @Override
    public List<StockInTotal> selectAndStatistics(StockInVo stockInVo) {
        List<StockInVo> stockInVoList = select(stockInVo);
        List<StockInTotal> statistics = statistics(stockInVoList);
        statistics.sort((a, b) -> Math.toIntExact(a.getMaxHour() - b.getMaxHour()));
        Collections.reverse(statistics);
        return statistics;
    }

    @Override
    public List<StockInVo> select(StockInVo stockInVo) {
        QueryWrapper<StockIn> queryWrapper = new QueryWrapper<>();
        if (!Objects.isNull(stockInVo.getStatus()) && stockInVo.getStatus() >= 0) {
            queryWrapper.eq("status", stockInVo.getStatus());
        }
        if (!Objects.isNull(stockInVo.getBillId())) {
            queryWrapper.eq(BusinessConstants.BILL_ID_FIELD, stockInVo.getBillId());
        }
        if (!Objects.isNull(stockInVo.getSupplier())) {
            queryWrapper.eq("supplier", stockInVo.getSupplier());
        }
        if (!Objects.isNull(stockInVo.getProductType())) {
            queryWrapper.eq("product_type", stockInVo.getProductType());
        }
        if (!Objects.isNull(stockInVo.getProductName())) {
            queryWrapper.eq("product_name", stockInVo.getProductName());
        }
        if (!Objects.isNull(stockInVo.getCreateTime())) {
            String startString = stockInVo.getCreateTime() + BusinessConstants.START_TIME_SUFFIX;
            String endString = stockInVo.getCreateTime() + BusinessConstants.END_TIME_SUFFIX;
            queryWrapper.between(BusinessConstants.CREATE_TIME_FIELD, startString, endString);
        }
        queryWrapper.orderByDesc(BusinessConstants.CREATE_TIME_FIELD);
        List<StockIn> stockInList = stockInMapper.selectList(queryWrapper);
        List<StockInVo> stockInVoList = new ArrayList<>();
        Map<Long, String> billNameMap = new HashMap<>();
        for (StockIn stockIn : stockInList) {
            StockInVo stock = new StockInVo();
            BeanUtils.copyProperties(stockIn, stock);
            if (!billNameMap.containsKey(stockIn.getBillId())) {
                StockInBill stockInBill = stockInBillMapper.selectById(stockIn.getBillId());
                billNameMap.put(stockIn.getBillId(), stockInBill.getBillName());
            }
            if (BusinessConstants.STOCK_IN_STATUS == stockIn.getStatus()) {
                Duration between = Duration.between(stockIn.getCreateTime(), LocalDateTime.now());
                stock.setStockHour(between.toHours());
            }
            stock.setBillName(billNameMap.get(stockIn.getBillId()));
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            stock.setCreateTime(stockIn.getCreateTime().format(fmt));
            stockInVoList.add(stock);
        }
        return stockInVoList;
    }

    @Override
    public int updateStatus(String orderId, int status) {
        return stockInMapper.update(null, new LambdaUpdateWrapper<StockIn>()
                .eq(StockIn::getOrderId, orderId)
                .set(StockIn::getStatus, status));
    }

    /**
     * 统计
     *
     * @param stockInVoList
     * @return
     */
    public List<StockInTotal> statistics(List<StockInVo> stockInVoList) {
        List<StockInTotal> stockInTotalList = new ArrayList<>();
        Map<String, List<StockInVo>> stringListMap = stockInVoList.stream().collect(Collectors.groupingBy(StockInVo::getProductType));
        for (Map.Entry<String, List<StockInVo>> map : stringListMap.entrySet()) {
            StockInTotal stockInTotal = new StockInTotal();
            List<StockInVo> value = map.getValue();
            List<StockInVo> collect = value.stream().sorted(Comparator.comparing(StockInVo::getCreateTime)).collect(Collectors.toList());
            // 总重量
            Optional<Double> totalWeight = value.stream().map(StockInVo::getProductWeight).reduce(Double::sum);
            // 总只数
            Optional<Integer> totalCount = value.stream().map(StockInVo::getProductCount).reduce(Integer::sum);
            stockInTotal.setProductName(value.get(0).getProductName());
            stockInTotal.setProductType(map.getKey());
            stockInTotal.setProductSize(value.get(0).getProductSize());
            stockInTotal.setWeight(totalWeight.get());
            stockInTotal.setCount(totalCount.get());
            stockInTotal.setAmount(value.size());
            stockInTotal.setMaxHour(calcLocalDateTime(collect.get(0).getCreateTime(), LocalDateTime.now()));
            stockInTotal.setStockIns(collect);
            stockInTotalList.add(stockInTotal);
        }
        return stockInTotalList;
    }

    /**
     * 计算两个日期时间的相差天数与小时
     *
     * @param startTimeString 开始时间
     * @param endTime   结束时间
     * @return String
     */
    public long calcLocalDateTime(String startTimeString, LocalDateTime endTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeString, fmt);
        Duration between = Duration.between(startTime, endTime);
        return between.toHours();
    }
}
