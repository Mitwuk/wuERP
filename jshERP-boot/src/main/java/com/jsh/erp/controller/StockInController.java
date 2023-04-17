package com.jsh.erp.controller;

import com.jsh.erp.datasource.vo.StockInVo;
import com.jsh.erp.service.stockIn.StockInService;
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
@RequestMapping(value = "/stockin")
@Slf4j
@Api(tags = {"入库管理"})
public class StockInController {

    @Autowired
    private StockInService stockInService;

    /**
     * 查询入库信息
     *
     * @param stockInVo
     * @return
     */
    @GetMapping(value = "/select")
    @ApiOperation(value = "查询入库信息")
    public BaseResponseInfo selectStockIn(StockInVo stockInVo) {
        BaseResponseInfo res = new BaseResponseInfo();
        return res;
    }

    /**
     * 创建入库单
     *
     * @param stockInVo
     * @return
     */
    @PostMapping(value = "/create")
    @ApiOperation(value = "创建入库单")
    public BaseResponseInfo createStockIn(StockInVo stockInVo) {
        BaseResponseInfo res = new BaseResponseInfo();
        stockInService.create(stockInVo);
        return res;
    }

    /**
     * 上传入库信息
     *
     * @param stockInVo
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation(value = "上传入库信息")
    public BaseResponseInfo uploadStockIn(StockInVo stockInVo) {
        BaseResponseInfo res = new BaseResponseInfo();
        stockInService.upload(stockInVo);
        return res;
    }

}
