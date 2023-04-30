package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockOutVo {
    private long id;
    private Long billId;
    private String billName;
    private String orderId;
    private String productType;
    private String productName;
    private String productSize;
    private int productCount;
    private double productWeight;
    /**
     * 入库流水号
     */
    private String stockInOrderId;
    private String customer;
    private int status;
    private String description;
    private String modifyTime;
    private String createTime;
}
