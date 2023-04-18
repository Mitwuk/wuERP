package com.jsh.erp.datasource.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.vo.StockOutVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockOutMapper extends BaseMapper<StockOut> {
}
