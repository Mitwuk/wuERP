package com.jsh.erp.controller;

import com.jsh.erp.datasource.common.ResponseBean;
import com.jsh.erp.datasource.vo.StockOutVo;
import com.jsh.erp.service.stockOut.StockOutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/stockout")
@Slf4j
@Api(tags = {"出库管理"})
public class StockOutController {

    @Autowired
    private StockOutService stockOutService;


    /**
     * 上传出库信息
     *
     * @param stockOutVoList
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "上传出库信息")
    public ResponseBean<Boolean> add(@RequestBody List<StockOutVo> stockOutVoList) {
        return ResponseBean.ok(stockOutService.add(stockOutVoList));
    }

    /**
     * 导出出库信息
     *
     * @param stockOutVo
     * @param response
     */
    @PostMapping(value = "/export")
    @ApiOperation(value = "导出出库信息")
    public void exportStockOut(StockOutVo stockOutVo, HttpServletResponse response) {
        // TODO
    }

}
