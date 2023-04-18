package com.jsh.erp.datasource.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "my_stock_in_bill")
public class StockInBill {
    private int id;
    private String billType;
    private String billName;
    private String createName;
    private LocalDateTime createTime;
}
