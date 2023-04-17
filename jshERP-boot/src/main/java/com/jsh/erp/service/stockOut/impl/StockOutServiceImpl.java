package com.jsh.erp.service.stockOut.impl;

import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.mappers.StockOutMapper;
import com.jsh.erp.datasource.vo.StockOutVo;
import com.jsh.erp.service.stockOut.StockOutService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockOutServiceImpl implements StockOutService {

    @Autowired
    private StockOutMapper stockOutMapper;

    @Override
    public int create(StockOutVo stockOutVo) {
        StockOut stockOut = new StockOut();
        BeanUtils.copyProperties(stockOutVo, stockOut);
        stockOut.setCreateName("test123");
        stockOut.setCreateTime(LocalDateTime.now());
        return stockOutMapper.insert(stockOut);
    }

    @Override
    public int upload(StockOutVo stockOutVo) {
        StockOut stockOut = new StockOut();
        BeanUtils.copyProperties(stockOutVo, stockOut);
        stockOut.setModifyName("test123");
        stockOut.setModifyTime(LocalDateTime.now());
        return stockOutMapper.insert(stockOut);
    }

    @Override
    public List<StockOut> select(StockOutVo stockOutVo) {
        return null;
    }
}
