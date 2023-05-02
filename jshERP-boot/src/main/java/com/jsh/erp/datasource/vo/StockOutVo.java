package com.jsh.erp.datasource.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class StockOutVo {
    private long id;
    private Long billId;
    private String billName;
    @JsonIgnore
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
    @JsonIgnore
    private int status;
    private String description;
    @JsonIgnore
    private String modifyTime;
    @JsonIgnore
    private String modifyName;
    @JsonIgnore
    private String createName;
    private String createTime;
}
