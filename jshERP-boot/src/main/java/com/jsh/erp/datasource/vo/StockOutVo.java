package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockOutVo {
    private long id;
    private String billId;
    private String orderId;
    /**
     * 入库流水号
     */
    private String stockInOrderId;
    private String customer;
    private String description;
    private LocalDateTime startModifyTime;
    private LocalDateTime endModifyTime;
    private LocalDateTime startCreateTime;
    private LocalDateTime endCreateTime;
}
