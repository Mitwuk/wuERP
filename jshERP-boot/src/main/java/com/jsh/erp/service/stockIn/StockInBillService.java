package com.jsh.erp.service.stockIn;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jsh.erp.datasource.entities.StockInBill;
import com.jsh.erp.datasource.vo.StockInBillVo;

public interface StockInBillService {

    IPage<StockInBill> selectByPage(StockInBillVo stockInBillVo);

    int add(StockInBillVo stockInBillVo);

    boolean delete(long id);
}
