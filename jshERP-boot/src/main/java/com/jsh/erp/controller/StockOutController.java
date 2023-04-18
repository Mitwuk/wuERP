package com.jsh.erp.controller;

import com.jsh.erp.datasource.common.ResponseBean;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.entities.StockOut;
import com.jsh.erp.datasource.vo.StockInTotal;
import com.jsh.erp.datasource.vo.StockInVo;
import com.jsh.erp.datasource.vo.StockOutVo;
import com.jsh.erp.service.stockIn.StockInService;
import com.jsh.erp.service.stockOut.StockOutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.jsh.erp.constants.BusinessConstants.STOCK_OUT_STATUS;

@RestController
@RequestMapping(value = "/stockout")
@Slf4j
@Api(tags = {"出库管理"})
public class StockOutController {

    @Autowired
    private StockOutService stockOutService;

    @Autowired
    private StockInService stockInService;

    /**
     * 查询出库列表和详情
     *
     * @return
     */
    @GetMapping(value = "/select")
    @ApiOperation(value = "查询出库信息")
    public ResponseBean<List<StockOutVo>> select(StockInVo stockInVo) {
        stockInVo.setStatus(STOCK_OUT_STATUS);
        List<StockIn> stockInList = stockInService.select(stockInVo);
        return ResponseBean.ok(stockOutService.selectByOrderId(stockInList));
    }

    /**
     * 查询出库信息
     *
     * @return
     */
    @GetMapping(value = "/detail")
    @ApiOperation(value = "查询出库详情")
    public ResponseBean<StockOut> detail(String stockInOrderId) {
        return ResponseBean.ok(stockOutService.select(stockInOrderId));
    }

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
