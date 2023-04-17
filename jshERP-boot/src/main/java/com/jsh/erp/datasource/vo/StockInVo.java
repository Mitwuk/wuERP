package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StockInVo implements Serializable {

    private static final long serialVersionUID = -4707963744247341571L;
    private Long id;
    private String productType;
    private String productName;
    private Integer productAmount;
    private Double productWeight;
    private Integer isOther;
    private String description;
    private String customer;
    private LocalDateTime modifyTime;
    private LocalDateTime endModifyTime;
    private LocalDateTime createTime;
    private LocalDateTime endCreateTime;
}
