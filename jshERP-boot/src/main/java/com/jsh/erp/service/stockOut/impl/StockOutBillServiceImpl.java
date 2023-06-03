package com.jsh.erp.service.stockOut.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.entities.StockOutBill;
import com.jsh.erp.datasource.mappers.StockOutBillMapper;
import com.jsh.erp.datasource.mappers.StockOutMapper;
import com.jsh.erp.datasource.vo.StockOutBillVo;
import com.jsh.erp.datasource.vo.StockOutDetailVo;
import com.jsh.erp.datasource.vo.StockOutVo;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.stockOut.StockOutBillService;
import com.jsh.erp.utils.ParamUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StockOutBillServiceImpl extends ServiceImpl<StockOutBillMapper, StockOutBill> implements StockOutBillService {

    @Autowired
    private StockOutBillMapper stockOutBillMapper;
    @Autowired
    private StockOutMapper stockOutMapper;

    @Override
    public IPage<StockOutBill> selectByPage(StockOutBillVo stockOutBillVo) {
        QueryWrapper<StockOutBill> queryWrapper = new QueryWrapper<>();
        Page<StockOutBill> page = new Page<>(stockOutBillVo.getPageNum(), stockOutBillVo.getPageSize());
        page.setDesc(BusinessConstants.CREATE_TIME_FIELD);
        return this.page(page, queryWrapper);
    }

    @Override
    public List<StockOutDetailVo> queryStockOutDetail(StockOutBillVo stockOutBillVo) {
        if (!StringUtils.isEmpty(stockOutBillVo.getCreateTime())) {
            String startString = stockOutBillVo.getCreateTime() + BusinessConstants.START_TIME_SUFFIX;
            String endString = stockOutBillVo.getCreateTime() + BusinessConstants.END_TIME_SUFFIX;
            stockOutBillVo.setStartTime(startString);
            stockOutBillVo.setEndTime(endString);
        }
        String offset = ParamUtils.getPageOffset(stockOutBillVo.getPageNum(), stockOutBillVo.getPageSize());
        stockOutBillVo.setOffset(Integer.parseInt(offset));
        List<StockOutBill> stockOutBillList = stockOutBillMapper.queryStockOutBill(stockOutBillVo);
        List<StockOutDetailVo> stockOutDetailVoList = new ArrayList<>();
        stockOutBillList.forEach(stockOutBill -> {
            StockOutDetailVo stockOutDetailVo = new StockOutDetailVo();
            BeanUtils.copyProperties(stockOutBill, stockOutDetailVo);
            stockOutDetailVo.setId(stockOutBill.getId());
            stockOutDetailVo.setCreateTime(stockOutBill.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            List<StockOutVo> sos = new ArrayList<>();
            List<StockOut> stockOuts = stockOutMapper.selectList(new QueryWrapper<StockOut>().lambda()
                    .eq(StockOut::getBillId, stockOutBill.getId()));
            stockOuts.forEach(stockOut -> {
                stockOut.setCustomer(stockOutBill.getCustomer());
                StockOutVo stockOutVo = new StockOutVo();
                BeanUtils.copyProperties(stockOut, stockOutVo);
                stockOutVo.setBillName(stockOutBill.getBillName());
                stockOutVo.setCreateTime(stockOut.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                sos.add(stockOutVo);
            });
            Collections.reverse(sos);
            stockOutDetailVo.setStockOutList(sos);
            stockOutDetailVoList.add(stockOutDetailVo);
        });
        return stockOutDetailVoList;
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
        stockOutBillMapper.insert(stockOutBill);
        return stockOutBill.getId();
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
