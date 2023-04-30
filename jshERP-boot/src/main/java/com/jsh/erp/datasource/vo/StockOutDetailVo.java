package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StockOutDetailVo extends StockOutBillVo implements Serializable {
    private List<StockOutVo> stockOutList;
}
