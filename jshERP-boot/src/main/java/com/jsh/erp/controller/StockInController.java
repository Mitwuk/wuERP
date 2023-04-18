package com.jsh.erp.controller;

import com.jsh.erp.datasource.common.ResponseBean;
import com.jsh.erp.datasource.vo.StockInTotal;
import com.jsh.erp.datasource.vo.StockInVo;
import com.jsh.erp.service.stockIn.StockInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.jsh.erp.constants.BusinessConstants.STOCK_IN_STATUS;
import static com.jsh.erp.constants.BusinessConstants.STOCK_STATUS;

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
     * @return
     */
    @GetMapping(value = "/select/stockin")
    @ApiOperation(value = "查询入库信息")
    public ResponseBean<List<StockInTotal>> selectStockIn() {
        return ResponseBean.ok(stockInService.selectByStatus(STOCK_STATUS));
    }

    /**
     * 查询入库信息
     *
     * @return
     */
    @GetMapping(value = "/select/stock")
    @ApiOperation(value = "查询库存信息")
    public ResponseBean<List<StockInTotal>> selectStock() {
        return ResponseBean.ok(stockInService.selectByStatus(STOCK_IN_STATUS));
    }

    /**
     * 上传入库信息
     *
     * @param stockInList
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加入库信息")
    public ResponseBean<Boolean> add(@RequestBody List<StockInVo> stockInList) {
        return ResponseBean.ok(stockInService.upload(stockInList));
    }
}
