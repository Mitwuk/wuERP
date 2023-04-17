package com.jsh.erp.datasource.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.vo.StockInVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockInMapper extends BaseMapper<StockIn> {
    /**
     * 创建入库单
     *
     * @param stockIn
     * @return
     */
    int create(StockIn stockIn);

    /**
     * 上传入库信息
     *
     * @param stockIn
     * @return
     */
    int upload(StockIn stockIn);

    /**
     * 根据条件查询
     *
     * @param stockInVo
     * @return
     */
    List<StockIn> select(StockInVo stockInVo);
}
