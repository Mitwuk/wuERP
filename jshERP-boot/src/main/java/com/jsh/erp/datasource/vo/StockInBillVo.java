package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.common.QueryRequest;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StockInBillVo extends QueryRequest implements Serializable {
    private static final long serialVersionUID = -8259171073956362124L;
    private long id;
    private String billType;
    private String billName;
    private String createName;
    private LocalDateTime createTime;
}
