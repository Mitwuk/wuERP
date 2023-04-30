package com.jsh.erp.service.stockOut;

import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.vo.StockInTotal;
import com.jsh.erp.datasource.vo.StockInVo;
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
     * @param stockOutVo
     * @return
     */
    List<StockOutVo> select(StockOutVo stockOutVo);

    /**
     * 根据条件查询（统计）
     * @param stockOutVo
     * @return
     */
    List<StockInTotal> selectAndStatistics(StockOutVo stockOutVo);


    List<StockOutVo> selectByOrderId(List<StockIn> stockIns);
}
