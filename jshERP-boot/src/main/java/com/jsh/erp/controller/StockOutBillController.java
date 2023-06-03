package com.jsh.erp.controller;

import com.jsh.erp.datasource.common.ResponseBean;
import com.jsh.erp.datasource.vo.StockOutBillVo;
import com.jsh.erp.datasource.vo.StockOutDetailVo;
import com.jsh.erp.service.stockOut.StockOutBillService;
import com.jsh.erp.utils.QueryUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static com.jsh.erp.constants.ExceptionConstants.*;

@RestController
@RequestMapping(value = "/stockout/bill")
@Slf4j
@Api(tags = {"出库单管理"})
public class StockOutBillController {

    @Autowired
    private StockOutBillService stockOutBillService;

    @GetMapping(value = "/select")
    @ApiOperation(value = "分页查询出库单列表")
    public ResponseBean<Object> selectByPage(StockOutBillVo stockOutBillVo) {
        Map<String, Object> dataTable = QueryUtils.getDataTable(stockOutBillService.selectByPage(stockOutBillVo));
        return ResponseBean.ok(dataTable);
    }

    @GetMapping(value = "/select/detail")
    @ApiOperation(value = "分页查询出库单列表（详情）")
    public ResponseBean<List<StockOutDetailVo>> selectDetail(StockOutBillVo stockOutBillVo) {
        return ResponseBean.ok(stockOutBillService.queryStockOutDetail(stockOutBillVo));
    }

    @GetMapping("/index")
    @ApiOperation(value = "分页查询出库单列表（详情）")
    public ModelAndView selectDetailWeb1(StockOutBillVo stockOutBillVo) {
        ModelAndView mv = new ModelAndView();
        List<StockOutDetailVo> stockOutDetailVos = stockOutBillService.queryStockOutDetail(stockOutBillVo);
        mv.addObject("data", stockOutDetailVos);
        mv.setViewName("/index.html");
        return mv;
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "创建出库单")
    public ResponseBean<Integer> add(@RequestBody StockOutBillVo stockOutBillVo) {
        return ResponseBean.ok(stockOutBillService.add(stockOutBillVo));
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除出库单")
    public ResponseBean<Object> delete(long id) {
        if (stockOutBillService.delete(id)) {
            return new ResponseBean<Object>(SERVICE_SUCCESS_CODE, SERVICE_SUCCESS_MSG);
        }
        return new ResponseBean<Object>(USER_DELETE_FAILED_CODE, "删除失败");
    }
}
