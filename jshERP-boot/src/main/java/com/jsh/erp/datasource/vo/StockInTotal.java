package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StockInTotal implements Serializable {
    private static final long serialVersionUID = 8469044149318366527L;
    private String productType;
    private String productName;
    private double weight;
    private int count;
    private int amount;
    private long maxHour;
    private List<StockInVo> stockIns;
}
