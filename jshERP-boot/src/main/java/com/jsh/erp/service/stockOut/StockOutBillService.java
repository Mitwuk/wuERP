package com.jsh.erp.service.stockOut;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jsh.erp.datasource.entities.StockOutBill;
import com.jsh.erp.datasource.vo.StockOutBillVo;
import com.jsh.erp.datasource.vo.StockOutDetailVo;

import java.util.List;

public interface StockOutBillService {
    IPage<StockOutBill> selectByPage(StockOutBillVo stockOutBillVo);

    List<StockOutDetailVo> queryStockOutDetail(StockOutBillVo stockOutBillVo);

    int add(StockOutBillVo stockOutBillVo);

    boolean delete(long id);
}
