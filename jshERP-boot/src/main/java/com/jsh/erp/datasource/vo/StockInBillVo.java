package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.common.QueryRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockInBillVo extends QueryRequest implements Serializable {
    private static final long serialVersionUID = -8259171073956362124L;
    private long id;
    private String billType;
    private String billName;
    private String productName;
    private String productType;
    private String supplier;
    private String createName;
    private String createTime;
    private String startTime;
    private String endTime;
}
