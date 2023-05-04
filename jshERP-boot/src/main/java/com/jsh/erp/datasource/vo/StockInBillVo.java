package com.jsh.erp.datasource.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsh.erp.datasource.common.QueryRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockInBillVo extends QueryRequest implements Serializable {
    private static final long serialVersionUID = -8259171073956362124L;
    private long id;
    private String billType;
    private String billName;
    private String supplier;
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
