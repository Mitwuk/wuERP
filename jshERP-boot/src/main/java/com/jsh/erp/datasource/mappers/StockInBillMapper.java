package com.jsh.erp.datasource.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsh.erp.datasource.entities.StockInBill;
import com.jsh.erp.datasource.vo.StockInDetailVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockInBillMapper extends BaseMapper<StockInBill> {

    List<StockInDetailVo> queryStockInDetail();
}
