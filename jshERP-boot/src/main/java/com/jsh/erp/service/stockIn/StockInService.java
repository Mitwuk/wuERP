package com.jsh.erp.service.stockIn;

import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.vo.StockInVo;

import java.util.List;

public interface StockInService {
    /**
     * 创建入库单
     * @param stockIn
     * @return
     */
    int create(StockInVo stockInVo);

    /**
     * 上传入库信息
     * @param stockIn
     * @return
     */
    int upload(StockInVo stockInVo);

    /**
     * 根据条件查询
     * @param stockInVo
     * @return
     */
    List<StockIn> select(StockInVo stockInVo);
}
