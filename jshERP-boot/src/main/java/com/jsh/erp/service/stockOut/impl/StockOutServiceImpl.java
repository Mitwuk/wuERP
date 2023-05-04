package com.jsh.erp.service.stockOut.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.entities.StockOutBill;
import com.jsh.erp.datasource.mappers.StockInMapper;
import com.jsh.erp.datasource.mappers.StockOutBillMapper;
import com.jsh.erp.datasource.mappers.StockOutMapper;
import com.jsh.erp.datasource.vo.StockInTotal;
import com.jsh.erp.datasource.vo.StockOutTotal;
import com.jsh.erp.datasource.vo.StockOutVo;
import com.jsh.erp.service.stockIn.StockInService;
import com.jsh.erp.service.stockOut.StockOutService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.jsh.erp.constants.BusinessConstants.STOCK_OUT_STATUS;

@Service
public class StockOutServiceImpl extends ServiceImpl<StockOutMapper, StockOut> implements StockOutService {

    @Autowired
    private StockOutMapper stockOutMapper;
    @Autowired
    private StockOutBillMapper stockOutBillMapper;
    @Autowired
    private StockInService stockInService;
    @Autowired
    private StockInMapper stockInMapper;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public boolean add(List<StockOutVo> stockOutVoList) {
        List<StockOut> stockOutList = new ArrayList<>();
        for (StockOutVo stockOutVo : stockOutVoList) {
            StockOut stockOut = new StockOut();
            BeanUtils.copyProperties(stockOutVo, stockOut);
            stockOut.setCreateTime(LocalDateTime.now());
            stockInService.updateStatus(stockOutVo.getStockInOrderId(), STOCK_OUT_STATUS);
            stockOutList.add(stockOut);
        }
        return this.saveBatch(stockOutList);
    }

    @Override
    public List<StockOutVo> select(StockOutVo stockOutVo) {
        // 根据创建时间


        QueryWrapper<StockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", STOCK_OUT_STATUS);
        if (!Objects.isNull(stockOutVo.getBillId())) {
            queryWrapper.eq(BusinessConstants.BILL_ID_FIELD, stockOutVo.getBillId());
        }
        if (!Objects.isNull(stockOutVo.getCustomer())) {
            queryWrapper.eq("customer", stockOutVo.getCustomer());
        }
        if (!Objects.isNull(stockOutVo.getProductType())) {
            queryWrapper.eq("product_type", stockOutVo.getProductType());
        }
        if (!Objects.isNull(stockOutVo.getProductName())) {
            queryWrapper.eq("product_name", stockOutVo.getProductName());
        }
        if (!Objects.isNull(stockOutVo.getProductSize())) {
            queryWrapper.eq("product_size", stockOutVo.getProductSize());
        }
        if (!Objects.isNull(stockOutVo.getCreateTime())) {
            String startString = stockOutVo.getCreateTime() + BusinessConstants.START_TIME_SUFFIX;
            String endString = stockOutVo.getCreateTime() + BusinessConstants.END_TIME_SUFFIX;
            queryWrapper.between(BusinessConstants.CREATE_TIME_FIELD, startString, endString);
        }
        queryWrapper.orderByDesc(BusinessConstants.CREATE_TIME_FIELD);
        List<StockIn> stockInList = stockInMapper.selectList(queryWrapper);
        List<StockOutVo> stockInVoList = new ArrayList<>();
        Map<Long, String> billNameMap = new HashMap<>();
        for (StockIn stockIn : stockInList) {
            StockOutVo stock = new StockOutVo();
            BeanUtils.copyProperties(stockIn, stock);
            // 根据入库流水号查询出库表数据
            QueryWrapper<StockOut> qw = new QueryWrapper<>();
            qw.eq("stock_in_order_id", stockIn.getOrderId());
            StockOut stockOut = stockOutMapper.selectOne(qw);

            if (!billNameMap.containsKey(stockIn.getOrderId())) {
                StockOutBill stockOutBill = stockOutBillMapper.selectById(stockIn.getBillId());
                billNameMap.put(stockIn.getBillId(), stockOutBill.getBillName());
            }
            stock.setBillName(billNameMap.get(stockIn.getBillId()));
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            stock.setCreateTime(stockIn.getCreateTime().format(fmt));
            stockInVoList.add(stock);
        }
        return stockInVoList;
    }

    @Override
    public List<StockInTotal> selectAndStatistics(StockOutVo stockOutVo) {

        return null;
    }

    @Override
    public List<StockOutVo> selectByOrderId(List<StockIn> stockIns) {
        List<StockOutVo> stockOutVoList = new ArrayList<>();
        for (StockIn stockIn : stockIns) {
            StockOutVo stockOutVo = new StockOutVo();
            BeanUtils.copyProperties(stockIn, stockOutVo);
            QueryWrapper<StockOut> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("stock_in_order_id", stockOutVo.getOrderId());
            StockOut stockOut = stockOutMapper.selectOne(queryWrapper);
            BeanUtils.copyProperties(stockOut, stockOutVo);
            stockOutVoList.add(stockOutVo);
        }
        return stockOutVoList;
    }

    /**
     * 统计
     *
     * @param stockOutVoList
     * @return
     */
    public List<StockOutTotal> statistics(List<StockOutVo> stockOutVoList) {
        List<StockOutTotal> stockInTotalList = new ArrayList<>();
        Map<String, List<StockOutVo>> stringListMap = stockOutVoList.stream().collect(Collectors.groupingBy(StockOutVo::getProductType));
        for (Map.Entry<String, List<StockOutVo>> map : stringListMap.entrySet()) {
            StockOutTotal stockOutTotal = new StockOutTotal();
            List<StockOutVo> value = map.getValue();
            List<StockOutVo> collect = value.stream().sorted(Comparator.comparing(StockOutVo::getCreateTime)).collect(Collectors.toList());
            // 总重量
            Optional<Double> totalWeight = value.stream().map(StockOutVo::getProductWeight).reduce(Double::sum);
            // 总只数
            Optional<Integer> totalCount = value.stream().map(StockOutVo::getProductCount).reduce(Integer::sum);
            stockOutTotal.setProductName(value.get(0).getProductName());
            stockOutTotal.setProductType(map.getKey());
            stockOutTotal.setWeight(totalWeight.get());
            stockOutTotal.setCount(totalCount.get());
            stockOutTotal.setAmount(value.size());
            stockOutTotal.setMaxHour(calcLocalDateTime(collect.get(0).getCreateTime(), LocalDateTime.now()));
            stockOutTotal.setStockOuts(collect);
            stockInTotalList.add(stockOutTotal);
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
