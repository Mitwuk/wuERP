package com.jsh.erp.controller;

import com.jsh.erp.datasource.common.ResponseBean;
import com.jsh.erp.datasource.vo.StockInBillVo;
import com.jsh.erp.datasource.vo.StockInDetailVo;
import com.jsh.erp.service.stockIn.StockInBillService;
import com.jsh.erp.utils.QueryUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.jsh.erp.constants.ExceptionConstants.*;

@RestController
@RequestMapping(value = "/stockin/bill")
@Slf4j
@Api(tags = {"入库单管理"})
public class StockInBillController {

    @Autowired
    private StockInBillService stockInBillService;

    @GetMapping(value = "/select")
    @ApiOperation(value = "分页查询入库单列表")
    public ResponseBean<Object> selectByPage(StockInBillVo stockInBillVo) {
        Map<String, Object> dataTable = QueryUtils.getDataTable(stockInBillService.selectByPage(stockInBillVo));
        return ResponseBean.ok(dataTable);
    }

    @GetMapping(value = "/select/detail")
    @ApiOperation(value = "分页查询入库单列表（详情）")
    public ResponseBean<Object> selectDetail(StockInBillVo stockInBillVo) {
        List<StockInDetailVo> stockInDetailVos = stockInBillService.queryStockInDetail(stockInBillVo);
        return ResponseBean.ok(stockInDetailVos);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "创建入库单")
    public ResponseBean<Long> add(@RequestBody StockInBillVo stockInBillVo) {
        return ResponseBean.ok(stockInBillService.add(stockInBillVo));
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除入库单")
    public ResponseBean<Object> delete(long id) {
        if (stockInBillService.delete(id)) {
            return new ResponseBean<Object>(SERVICE_SUCCESS_CODE, SERVICE_SUCCESS_MSG);
        }
        return new ResponseBean<Object>(USER_DELETE_FAILED_CODE, "删除失败");
    }
}
