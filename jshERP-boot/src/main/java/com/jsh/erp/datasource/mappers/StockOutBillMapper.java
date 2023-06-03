package com.jsh.erp.datasource.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsh.erp.datasource.entities.StockOutBill;
import com.jsh.erp.datasource.vo.StockOutBillVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockOutBillMapper extends BaseMapper<StockOutBill> {

    List<StockOutBill> queryStockOutBill(StockOutBillVo stockOutBillVo);
}