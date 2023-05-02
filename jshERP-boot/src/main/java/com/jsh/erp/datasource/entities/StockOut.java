package com.jsh.erp.datasource.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "my_stock_out")
public class StockOut {
    private long id;
    private long billId;
    private String orderId;
    private String stockInOrderId;
    private String productType;
    private String productName;
    private String productSize;
    private int productCount;
    private double productWeight;
    private String customer;
    private String modifyName;
    private LocalDateTime modifyTime;
    private String createName;
    private LocalDateTime createTime;
}
