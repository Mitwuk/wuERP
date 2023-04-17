package com.jsh.erp.service.stockOut;

import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.vo.StockOutVo;

import java.util.List;

public interface StockOutService {
    /**
     * 创建出库单
     * @param stockOutVo
     * @return
     */
    int create(StockOutVo stockOutVo);

    /**
     * 上传出库信息
     * @param stockOutVo
     * @return
     */
    int upload(StockOutVo stockOutVo);

    /**
     * 根据条件查询
     * @param stockOutVo
     * @return
     */
    List<StockOut> select(StockOutVo stockOutVo);
}
