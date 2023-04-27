package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.entities.StockIn;
import lombok.Data;

import java.util.List;

@Data
public class StockInDetailVo extends StockInBillVo {
    private static final long serialVersionUID = 2330917174436496127L;
    private List<StockIn> stockInList;
}
