package com.jsh.erp.service.stockOut.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.StockOutBill;
import com.jsh.erp.datasource.mappers.StockOutBillMapper;
import com.jsh.erp.datasource.vo.StockOutBillVo;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.stockOut.StockOutBillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class StockOutBillServiceImpl extends ServiceImpl<StockOutBillMapper, StockOutBill> implements StockOutBillService {

    @Autowired
    private StockOutBillMapper stockOutBillMapper;

    @Override
    public IPage<StockOutBill> selectByPage(StockOutBillVo stockOutBillVo) {
        QueryWrapper<StockOutBill> queryWrapper = new QueryWrapper<>();
        Page<StockOutBill> page = new Page<>(stockOutBillVo.getPageNum(), stockOutBillVo.getPageSize());
        page.setDesc("create_time");
        return this.page(page, queryWrapper);
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
    public int delete(long id) {
        return stockOutBillMapper.deleteById(id);
    }
}
