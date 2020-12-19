package com.jale.weblog.shopping.app.controller;

import com.jale.weblog.common.commonobjects.R;
import com.jale.weblog.shopping.api.service.OrderformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orderform")
@Api(tags = {"订单服务"})
public class OrderformController {

    @Reference
    private OrderformService orderformService;

    @GetMapping("/order/{shoppingCarId}")
    @ApiOperation("下单")
    public R order(@PathVariable @ApiParam(value = "购物车id", required = true) Integer shoppingCarId) {
        return R.success(orderformService.generateOrder(shoppingCarId));
    }

}
