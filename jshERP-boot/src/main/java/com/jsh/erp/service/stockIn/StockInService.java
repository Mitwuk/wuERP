package com.jsh.erp.service.stockIn;

import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.vo.StockInTotal;
import com.jsh.erp.datasource.vo.StockInVo;

import java.util.List;

public interface StockInService {
    /**
     * 上传入库信息
     *
     * @param stockInList
     * @return
     */
    boolean upload(List<StockInVo> stockInList);

    /**
     * 根据条件查询（统计）
     * @param stockInVo
     * @return
     */
    List<StockInTotal> selectAndStatistics(StockInVo stockInVo);

    /**
     * 根据条件查询
     * @param stockInVo
     * @return
     */
    List<StockInVo> select(StockInVo stockInVo);

    /**
     * 更新状态
     * @param orderId
     * @param status
     * @return
     */
    int updateStatus(String orderId, int status);
}
