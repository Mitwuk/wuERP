package com.jsh.erp.service.stockIn.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.mappers.StockInMapper;
import com.jsh.erp.datasource.vo.StockInTotal;
import com.jsh.erp.datasource.vo.StockInVo;
import com.jsh.erp.service.stockIn.StockInService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockInServiceImpl extends ServiceImpl<StockInMapper, StockIn> implements StockInService {

    @Autowired
    private StockInMapper stockInMapper;

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
    public List<StockInTotal> selectByStatus(int status) {
        List<StockInTotal> stockInTotalList = new ArrayList<>();
        Map<String, Object> columnMap = new HashMap<>();
        if (status >= 0) {
            columnMap.put("status", status);
        }
        List<StockIn> stockIns = stockInMapper.selectByMap(columnMap);
        Map<String, List<StockIn>> stringListMap = stockIns.stream().collect(Collectors.groupingBy(StockIn::getProductType));
        for (Map.Entry<String, List<StockIn>> map : stringListMap.entrySet()) {
            StockInTotal stockInTotal = new StockInTotal();
            List<StockIn> value = map.getValue();
            List<StockIn> collect = value.stream().sorted(Comparator.comparing(StockIn::getCreateTime)).collect(Collectors.toList());
            // 总重量
            Optional<Double> totalWeight = value.stream().map(StockIn::getProductWeight).reduce(Double::sum);
            // 总只数
            Optional<Integer> totalCount = value.stream().map(StockIn::getProductCount).reduce(Integer::sum);
            stockInTotal.setProductName(value.get(0).getProductName());
            stockInTotal.setProductType(map.getKey());
            stockInTotal.setWeight(totalWeight.get());
            stockInTotal.setCount(totalCount.get());
            stockInTotal.setAmount(value.size());
            stockInTotal.setMaxDay(calcLocalDateTime(collect.get(0).getCreateTime(), LocalDateTime.now()));
            stockInTotal.setStockIns(collect);
            stockInTotalList.add(stockInTotal);
        }
        return stockInTotalList;
    }

    @Override
    public int updateStatus(String orderId, int status) {
        return stockInMapper.update(null, new LambdaUpdateWrapper<StockIn>()
                .eq(StockIn::getOrderId, orderId)
                .set(StockIn::getStatus, status));
    }

    /**
     * 计算两个日期时间的相差天数与小时
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return String
     */
    public String calcLocalDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        Duration between = Duration.between(startTime, endTime);
        StringBuilder timeBuilder = new StringBuilder();

        long dayResult = between.toDays();
        if (dayResult > 0) {
            long betweenHours = between.toHours();
            long hourResult = dayResult * 24 - betweenHours;
            if (hourResult > 0) {
                timeBuilder.append(dayResult).append("天")
                        .append(" ").append(hourResult).append("小时");
            } else if (hourResult == 0) {
                timeBuilder.append(dayResult).append("天");
            } else {
                timeBuilder.append(dayResult).append("天")
                        .append(" ").append(-hourResult).append("小时");
            }
        } else {
            timeBuilder.append(between.toHours()).append("小时");
        }
        return timeBuilder.toString();
    }
}
