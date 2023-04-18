package com.jsh.erp.service.stockIn.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockInBill;
import com.jsh.erp.datasource.mappers.StockInBillMapper;
import com.jsh.erp.datasource.mappers.StockInMapper;
import com.jsh.erp.datasource.vo.StockInBillVo;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.stockIn.StockInBillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class StockInBillServiceImpl extends ServiceImpl<StockInBillMapper, StockInBill> implements StockInBillService {

    @Autowired
    private StockInBillMapper stockInBillMapper;
    @Autowired
    private StockInMapper stockInMapper;

    @Override
    public IPage<StockInBill> selectByPage(StockInBillVo stockInBillVo) {
        QueryWrapper<StockInBill> queryWrapper = new QueryWrapper<>();
        Page<StockInBill> page = new Page<>(stockInBillVo.getPageNum(), stockInBillVo.getPageSize());
        page.setDesc("create_time");
        return this.page(page, queryWrapper);
    }

    @Override
    public int add(StockInBillVo stockInBillVo) {
        if (StringUtils.isEmpty(stockInBillVo.getBillType()) || StringUtils.isEmpty(stockInBillVo.getBillName())) {
            throw new BusinessRunTimeException(ExceptionConstants.BILL_TYPE_OR_BILL_NAME_NOT_EMPTY_CODE,
                    ExceptionConstants.BILL_TYPE_OR_BILL_NAME_NOT_EMPTY_MSG);
        }
        StockInBill stockInBill = new StockInBill();
        BeanUtils.copyProperties(stockInBillVo, stockInBill);
        stockInBill.setCreateTime(LocalDateTime.now());
        return stockInBillMapper.insert(stockInBill);
    }

    @Override
    public boolean delete(long id) {
        QueryWrapper<StockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bill_id", id);
        // 根据billId删除入库表
        stockInMapper.delete(queryWrapper);
        return stockInBillMapper.deleteById(id) > 0;
    }
}
