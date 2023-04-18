package com.jsh.erp.datasource.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "my_stock_out_bill")
public class StockOutBill {
    private int id;
    private String billType;
    private String billName;
    private String createName;
    private LocalDateTime createTime;
}
