package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.common.QueryRequest;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StockOutBillVo extends QueryRequest implements Serializable {
    private static final long serialVersionUID = -5792037727357798751L;
    private long id;
    private String billType;
    private String billName;
    private String customer;
    private String createName;
    private LocalDateTime createTime;
}