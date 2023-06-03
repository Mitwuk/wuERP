package com.jsh.erp.datasource.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsh.erp.datasource.common.QueryRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockOutBillVo extends QueryRequest implements Serializable {
    private static final long serialVersionUID = -5792037727357798751L;
    private long id;
    private String billType;
    private String billName;
    private String customer;
    private String description;
    private String createName;
    private String createTime;
    @JsonIgnore
    private String productName;
    @JsonIgnore
    private String productSize;
    @JsonIgnore
    private String productType;
    @JsonIgnore
    private String startTime;
    @JsonIgnore
    private String endTime;

}