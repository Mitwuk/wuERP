package com.jsh.erp.controller;

import com.jsh.erp.datasource.common.ResponseBean;
import com.jsh.erp.datasource.entities.StockIn;
import com.jsh.erp.datasource.vo.StockInTotal;
import com.jsh.erp.datasource.vo.StockInVo;
import com.jsh.erp.service.stockIn.StockInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/stockin")
@Slf4j
@Api(tags = {"入库管理"})
public class StockInController {

    @Autowired
    private StockInService stockInService;

    /**
     * 查询入库列表和详情
     *
     * @return
     */
    @GetMapping(value = "/select/stockin")
    @ApiOperation(value = "查询入库信息")
    public ResponseBean<List<StockInVo>> selectStockIn(StockInVo stockInVo) {
        return ResponseBean.ok(stockInService.select(stockInVo));
    }

    /**
     * 统计和详情（库存：1，出库：0，入库：-1）
     *
     * @return
     */
    @GetMapping(value = "/statistics/stock")
    @ApiOperation(value = "查询库存信息")
    public ResponseBean<List<StockInTotal>> statisticsStock(StockInVo stockInVo) {
        return ResponseBean.ok(stockInService.selectAndStatistics(stockInVo));
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
