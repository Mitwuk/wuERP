package com.jsh.erp.service.stockOut.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.entities.StockOutBill;
import com.jsh.erp.datasource.mappers.StockOutBillMapper;
import com.jsh.erp.datasource.mappers.StockOutMapper;
import com.jsh.erp.datasource.vo.StockInVo;
import com.jsh.erp.datasource.vo.StockOutVo;
import com.jsh.erp.service.stockIn.StockInService;
import com.jsh.erp.service.stockOut.StockOutService;
import com.jsh.erp.utils.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jsh.erp.constants.BusinessConstants.STOCK_OUT_STATUS;

@Service
public class StockOutServiceImpl extends ServiceImpl<StockOutMapper, StockOut> implements StockOutService {

    @Autowired
    private StockOutMapper stockOutMapper;
    @Autowired
    private StockInService stockInService;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public boolean add(List<StockOutVo> stockOutVoList) {
        List<StockOut> stockOutList = new ArrayList<>();
        for (StockOutVo stockOutVo : stockOutVoList) {
            StockOut stockOut = new StockOut();
            BeanUtils.copyProperties(stockOutVo, stockOut);
            stockOut.setCreateTime(LocalDateTime.now());
            stockOutList.add(stockOut);
            stockInService.updateStatus(stockOutVo.getStockInOrderId(), STOCK_OUT_STATUS);
        }
        return this.saveBatch(stockOutList);
    }

    @Override
    public StockOut select(String stockInOrderId) {
        LambdaQueryWrapper<StockOut> wrapper = new LambdaQueryWrapper<StockOut>().eq(StockOut::getStockInOrderId, stockInOrderId);
        return stockOutMapper.selectOne(wrapper);
    }

    @Override
    public List<StockOutVo> selectByOrderId(List<StockIn> stockIns) {
        List<StockOutVo> stockOutVoList = new ArrayList<>();
        for (StockIn stockIn : stockIns) {
            StockOutVo stockOutVo = new StockOutVo();
            BeanUtils.copyProperties(stockIn, stockOutVo);
            StockOut stockOut = select(stockIn.getOrderId());
            BeanUtils.copyProperties(stockOut, stockOutVo);
            stockOutVoList.add(stockOutVo);
        }
        return stockOutVoList;
    }


}
