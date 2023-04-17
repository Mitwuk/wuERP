package com.jsh.erp.service.stockIn.impl;

import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.mappers.StockInMapper;
import com.jsh.erp.datasource.vo.StockInVo;
import com.jsh.erp.service.stockIn.StockInService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockInServiceImpl implements StockInService {

    @Autowired
    private StockInMapper stockInMapper;

    @Override
    public int create(StockInVo stockInVo) {
        StockIn stockIn = new StockIn();
        BeanUtils.copyProperties(stockInVo, stockIn);
        stockIn.setCreateName("test123");
        stockIn.setCreateTime(LocalDateTime.now());
        return stockInMapper.insert(stockIn);
    }

    @Override
    public int upload(StockInVo stockInVo) {
        StockIn stockIn = new StockIn();
        BeanUtils.copyProperties(stockInVo, stockIn);
        stockIn.setModifyName("test123");
        stockIn.setModifyTime(LocalDateTime.now());
        return stockInMapper.updateById(stockIn);
    }

    @Override
    public List<StockIn> select(StockInVo stockInVo) {
        return stockInMapper.select(stockInVo);
    }
}
