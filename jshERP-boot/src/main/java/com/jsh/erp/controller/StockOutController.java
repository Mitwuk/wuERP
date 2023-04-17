package com.jsh.erp.controller;

import com.jsh.erp.datasource.vo.StockOutVo;
import com.jsh.erp.service.stockOut.StockOutService;
import com.jsh.erp.utils.BaseResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stockout")
@Slf4j
@Api(tags = {"出库管理"})
public class StockOutController {

    @Autowired
    private StockOutService stockOutService;

    /**
     * 查询出库信息
     * @param stockOutVo
     * @return
     */
    @GetMapping(value = "/select")
    @ApiOperation(value = "查询出库信息")
    public BaseResponseInfo selectStockOut(StockOutVo stockOutVo) {
        BaseResponseInfo res = new BaseResponseInfo();
        return res;
    }

    /**
     * 创建出库单
     * @param stockOutVo
     * @return
     */
    @PostMapping(value = "/create")
    @ApiOperation(value = "创建出库单")
    public BaseResponseInfo createStockOut(StockOutVo stockOutVo) {
        BaseResponseInfo res = new BaseResponseInfo();
        stockOutService.create(stockOutVo);
        return res;
    }

    /**
     * 上传出库信息
     * @param stockOutVo
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation(value = "上传出库信息")
    public BaseResponseInfo uploadStockOut(StockOutVo stockOutVo) {
        BaseResponseInfo res = new BaseResponseInfo();
        stockOutService.upload(stockOutVo);
        return res;
    }

}
