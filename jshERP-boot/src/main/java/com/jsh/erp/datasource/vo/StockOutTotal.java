package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.entities.StockOut;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StockOutTotal implements Serializable {
    private static final long serialVersionUID = 4421173058762974204L;
    private String productType;
    private String productName;
    private double weight;
    private int count;
    private int amount;
    private String maxHour;
    private List<StockOut> stockOuts;
}