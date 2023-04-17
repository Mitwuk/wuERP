package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockOutVo {
    private long id;
    private String productType;
    private String productName;
    private int productAmount;
    private double productWeight;
    private boolean isOther;
    private String description;
    private String customer;
    private LocalDateTime startModifyTime;
    private LocalDateTime endModifyTime;
    private LocalDateTime startCreateTime;
    private LocalDateTime endCreateTime;
}
