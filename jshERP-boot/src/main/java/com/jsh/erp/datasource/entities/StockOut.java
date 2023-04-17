package com.jsh.erp.datasource.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockOut {
    private long id;
    private String productType;
    private String productName;
    private int productAmount;
    private double productWeight;
    private boolean isOther;
    private String description;
    private String customer;
    private String modifyName;
    private LocalDateTime modifyTime;
    private String createName;
    private LocalDateTime createTime;
}
