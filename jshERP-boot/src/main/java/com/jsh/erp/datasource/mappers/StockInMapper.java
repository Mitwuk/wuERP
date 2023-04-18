package com.jsh.erp.datasource.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.vo.StockInVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockInMapper extends BaseMapper<StockIn> {
}
