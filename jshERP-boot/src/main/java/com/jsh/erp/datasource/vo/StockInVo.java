package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StockInVo implements Serializable {
    private static final long serialVersionUID = -4707963744247341571L;
    private long id;
    private long billId;
    private String orderId;
    private String productType;
    private String productName;
    private int productCount;
    private double productWeight;
    private int isOther;
    private String description;
    private String customer;
    private int status;
    private LocalDateTime modifyTime;
    private LocalDateTime endModifyTime;
    private LocalDateTime createTime;
    private LocalDateTime endCreateTime;
}
