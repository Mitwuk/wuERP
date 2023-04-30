package com.jsh.erp.service.stockOut.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockInBill;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.entities.StockOutBill;
import com.jsh.erp.datasource.mappers.StockInMapper;
import com.jsh.erp.datasource.mappers.StockOutBillMapper;
import com.jsh.erp.datasource.mappers.StockOutMapper;
import com.jsh.erp.datasource.vo.*;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.stockOut.StockOutBillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class StockOutBillServiceImpl extends ServiceImpl<StockOutBillMapper, StockOutBill> implements StockOutBillService {

    @Autowired
    private StockOutBillMapper stockOutBillMapper;
    @Autowired
    private StockOutMapper stockOutMapper;
    @Autowired
    private StockInMapper stockInMapper;

    @Override
    public IPage<StockOutBill> selectByPage(StockOutBillVo stockOutBillVo) {
        QueryWrapper<StockOutBill> queryWrapper = new QueryWrapper<>();
        Page<StockOutBill> page = new Page<>(stockOutBillVo.getPageNum(), stockOutBillVo.getPageSize());
        page.setDesc(BusinessConstants.CREATE_TIME_FIELD);
        return this.page(page, queryWrapper);
    }

    @Override
    public List<StockOutDetailVo> queryStockOutDetail(StockOutBillVo stockOutBillVo) {
        return stockOutBillMapper.queryStockOutDetail(stockOutBillVo);
    }

    @Override
    public int add(StockOutBillVo stockOutBillVo) {
        if (StringUtils.isEmpty(stockOutBillVo.getBillType()) || StringUtils.isEmpty(stockOutBillVo.getBillName())) {
            throw new BusinessRunTimeException(ExceptionConstants.BILL_TYPE_OR_BILL_NAME_NOT_EMPTY_CODE,
                    ExceptionConstants.BILL_TYPE_OR_BILL_NAME_NOT_EMPTY_MSG);
        }
        StockOutBill stockOutBill = new StockOutBill();
        BeanUtils.copyProperties(stockOutBillVo, stockOutBill);
        stockOutBill.setCreateTime(LocalDateTime.now());
        return stockOutBillMapper.insert(stockOutBill);
    }

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public boolean delete(long id) {
        QueryWrapper<StockOut> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BusinessConstants.BILL_ID_FIELD, id);
        // 删除该出库单对应的
        stockOutMapper.delete(queryWrapper);
        return stockOutBillMapper.deleteById(id) > 0;
    }
}
