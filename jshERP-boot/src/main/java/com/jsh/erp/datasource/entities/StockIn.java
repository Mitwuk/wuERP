package com.jsh.erp.datasource.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "my_stock_in")
public class StockIn {
    private long id;
    private long billId;
    private String orderId;
    private String productType;
    private String productName;
    private int productCount;
    private double productWeight;
    private int isOther;
    private String description;
    private String supplier;
    private int status;
    private String modifyName;
    private LocalDateTime modifyTime;
    private String createName;
    private LocalDateTime createTime;
}
