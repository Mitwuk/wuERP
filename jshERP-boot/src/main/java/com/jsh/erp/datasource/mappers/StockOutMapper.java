package com.jsh.erp.datasource.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.vo.StockOutVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockOutMapper extends BaseMapper<StockOut> {
    /**
     * 创建出库单
     *
     * @param stockOut
     * @return
     */
    int create(StockOut stockOut);

    /**
     * 上传出库信息
     *
     * @param stockOut
     * @return
     */
    int upload(StockOut stockOut);

    /**
     * 根据条件查询
     *
     * @param stockOutVo
     * @return
     */
    List<StockOut> select(StockOutVo stockOutVo);
}
