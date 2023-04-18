package com.jsh.erp.service.stockOut;

import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.vo.StockOutVo;

import java.util.List;

public interface StockOutService {
    /**
     * 创建出库单
     * @param stockOutVoList
     * @return
     */
    boolean add(List<StockOutVo> stockOutVoList);

    /**
     * 根据条件查询
     * @param stockInOrderId
     * @return
     */
    StockOut select(String stockInOrderId);
}
