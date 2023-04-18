package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockOutVo {
    private long id;
    private long billId;
    private String orderId;
    private String productType;
    private String productName;
    private int productCount;
    private double productWeight;
    /**
     * 入库流水号
     */
    private String stockInOrderId;
    private String customer;
    private String description;
    private LocalDateTime modifyTime;
    private LocalDateTime createTime;
}
