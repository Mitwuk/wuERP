package com.jsh.erp.service.stockOut;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jsh.erp.datasource.entities.StockOutBill;
import com.jsh.erp.datasource.vo.StockOutBillVo;

public interface StockOutBillService {
    IPage<StockOutBill> selectByPage(StockOutBillVo stockOutBillVo);

    int add(StockOutBillVo stockOutBillVo);

    boolean delete(long id);
}
