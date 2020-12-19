package com.jale.weblog.shopping.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jale.weblog.common.commonobjects.PageVo;
import com.jale.weblog.common.commonobjects.R;
import com.jale.weblog.shopping.api.dataobject.Goods;
import com.jale.weblog.shopping.api.dto.GoodsSelectDto;
import com.jale.weblog.shopping.api.service.GoodsService;
import com.jale.weblog.shopping.api.service.ShoppingCarService;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = {"商品服务"})
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Reference
    private ShoppingCarService shoppingCarService;

    @ApiOperation("分页查询商品")
    @PostMapping("/selectPage")
    public R selectPage(@RequestBody @ApiParam("查询条件") GoodsSelectDto goodsSelectDto) {
        IPage<Goods> page = goodsService.selectPage(goodsSelectDto);
        return R.success(new PageVo(page));
    }

    @ApiOperation(value = "添加商品")
    @PostMapping("/insert")
    public R insert(@RequestBody @ApiParam(value = "商品", required = true) Goods goods) {
       return R.success(goodsService.insert(goods));
    }

    @ApiOperation(value = "查询商品")
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable @ApiParam(name = "id", value = "商品id", example = "1", required = true) Integer id) {
        return R.success(goodsService.selectById(id));
    }

    @ApiOperation(value = "删除商品")
    @GetMapping("/deleteById/{id}")
    public R deleteById(@PathVariable @ApiParam(name = "id", value = "商品id", example = "1", required = true) Integer id) {
        return R.success(goodsService.deleteById(id));
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径不存在"),
    })
    @ApiOperation(value = "更新商品")
    @PostMapping("/updateById")
    public R updateById(@RequestBody @ApiParam(value = "商品", required = true) Goods goods) throws IOException {
        return R.success(goodsService.updateById(goods));
    }

    @GetMapping("/saveToShoppingCar/{userId}/{goodsId}/{goodsCount}")
    @ApiOperation("添加购物车")
    public R saveToShoppingCar(@PathVariable @ApiParam(value = "用户id", required = true) Long userId,
                               @PathVariable @ApiParam(value = "商品id", required = true) Integer goodsId,
                               @PathVariable @ApiParam(value = "商品数量", required = true) Integer goodsCount) {
        shoppingCarService.saveToShoppingCar(userId, goodsId, goodsCount);
        return  R.success();
    }
}
