package com.jsh.erp.datasource.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "my_stock_in")
public class StockIn {
    private Long id;
    private String productType;
    private String productName;
    private Integer productAmount;
    private Double productWeight;
    private Integer isOther;
    private String description;
    private String customer;
    private String modifyName;
    private LocalDateTime modifyTime;
    private String createName;
    private LocalDateTime createTime;
}
