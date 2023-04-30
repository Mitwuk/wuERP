package com.jsh.erp.service.stockIn.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockInBill;
import com.jsh.erp.datasource.mappers.StockInBillMapper;
import com.jsh.erp.datasource.mappers.StockInMapper;
import com.jsh.erp.datasource.vo.StockInBillVo;
import com.jsh.erp.datasource.vo.StockInDetailVo;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.stockIn.StockInBillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        page.setDesc(BusinessConstants.CREATE_TIME_FIELD);
        return this.page(page, queryWrapper);
    }

    @Override
    public List<StockInDetailVo> queryStockInDetail(StockInBillVo stockInBillVo) {
        QueryWrapper<StockInBill> queryWrapper = new QueryWrapper<>();
        if (!Objects.isNull(stockInBillVo.getCreateTime())) {
            String startString = stockInBillVo.getCreateTime() + BusinessConstants.START_TIME_SUFFIX;
            String endString = stockInBillVo.getCreateTime() + BusinessConstants.END_TIME_SUFFIX;
            queryWrapper.between(BusinessConstants.CREATE_TIME_FIELD, startString, endString);
        }
        queryWrapper.orderByDesc(BusinessConstants.CREATE_TIME_FIELD);
        // 查询入库单
        List<StockInBill> stockInBillList = stockInBillMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(stockInBillList)) {
            return Collections.EMPTY_LIST;
        }
        // 对象转换
        List<StockInDetailVo> stockInDetail = new ArrayList<>();
        for (StockInBill stockInBill : stockInBillList) {
            StockInDetailVo stockInDetailVo = new StockInDetailVo();
            BeanUtils.copyProperties(stockInBill, stockInDetailVo);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            stockInDetailVo.setCreateTime(stockInBill.getCreateTime().format(fmt));
            stockInDetail.add(stockInDetailVo);
        }
        for (int i = 0; i < stockInDetail.size(); i++) {
            StockInDetailVo stockInDetailVo = stockInDetail.get(i);
            QueryWrapper<StockIn> queryStockIn = new QueryWrapper<>();
            if (!Objects.isNull(stockInBillVo.getSupplier())) {
                queryStockIn.eq("supplier", stockInBillVo.getSupplier());
            }
            if (!Objects.isNull(stockInBillVo.getProductName())) {
                queryStockIn.eq("product_name", stockInBillVo.getProductName());
            }
            queryStockIn.eq("bill_id", stockInDetailVo.getId());
            // 查询入库但详情
            List<StockIn> stockInList = stockInMapper.selectList(queryStockIn);
            if (CollectionUtils.isEmpty(stockInList) && (!Objects.isNull(stockInBillVo.getSupplier()) || !Objects.isNull(stockInBillVo.getProductName()))) {
                stockInDetail.remove(i);
                i--;
                continue;
            }
            if (CollectionUtils.isEmpty(stockInList)) {
                // 初始化
                stockInList = new ArrayList<>();
            }
            stockInDetailVo.setStockInList(stockInList);
        }
        return stockInDetail;
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
        queryWrapper.eq(BusinessConstants.BILL_ID_FIELD, id);
        // 根据billId删除入库表
        stockInMapper.delete(queryWrapper);
        return stockInBillMapper.deleteById(id) > 0;
    }
}
