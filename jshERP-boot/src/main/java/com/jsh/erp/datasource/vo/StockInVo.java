package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StockInVo implements Serializable {
    private static final long serialVersionUID = -4707963744247341571L;
    private long id;
    private Long billId;
    private String billName;
    private String orderId;
    private String productType;
    private String productName;
    private String productSize;
    private int productCount;
    private double productWeight;
    private String description;
    private String supplier;
    private Integer status;
    private long stockHour;
    private String createTime;
}
