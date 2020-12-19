package com.jale.weblog.shopping.service.impl;

import com.jale.weblog.common.exception.WeblogException;
import com.jale.weblog.shopping.api.dataobject.Goods;
import com.jale.weblog.shopping.api.dataobject.Orderform;
import com.jale.weblog.shopping.api.dataobject.ShoppingCar;
import com.jale.weblog.shopping.api.service.GoodsService;
import com.jale.weblog.shopping.api.service.OrderformService;
import com.jale.weblog.shopping.api.service.ShoppingCarService;
import com.jale.weblog.shopping.service.mapper.OrderformMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@org.apache.dubbo.config.annotation.Service
public class OrderformServiceImpl implements OrderformService {

    @Autowired
    private OrderformMapper orderformMapper;

    @Reference
    private ShoppingCarService shoppingCarService;

    @Reference
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void insert(Orderform orderform) {
        orderform.setAddTime(new Date());
        orderformMapper.insert(orderform);
    }

    @Override
    @Transactional
    public Integer generateOrder(Integer shoppingCarId) {
        ShoppingCar shoppingCar = shoppingCarService.findById(shoppingCarId);
        if (shoppingCar == null) {
            throw new WeblogException("购物车数据异常");
        }

        Goods goods = goodsService.selectById(shoppingCar.getGoodsId());
        if (goods == null) {
            throw new WeblogException("商品不存在");
        }

        if (goods.getStockCount().compareTo(shoppingCar.getStockCount()) < 0) {
            throw new WeblogException("商品库存不足,库存数 " + goods.getStockCount());
        }

        int newGoodsCount = goods.getStockCount() - shoppingCar.getStockCount();
        Map<String, Object> params = new HashMap<>();
        params.put("id", goods.getId());
        params.put("newStockCount", newGoodsCount);
        params.put("oldStockCount", goods.getStockCount());
        int i = goodsService.casUpdate(params);
        if (i < 1) {
            throw new WeblogException("业务繁忙,请稍后再试");
        }

        Orderform orderform = new Orderform();
        orderform.setOrderNumber((int) System.currentTimeMillis());
        orderform.setGoodsId(shoppingCar.getGoodsId());
        orderform.setGoodsName(shoppingCar.getGoodsName());
        orderform.setStockCount(shoppingCar.getStockCount());
        orderform.setUserId(shoppingCar.getUserId());
        orderform.setSinglePrice(goods.getPrice());
        orderform.setTotalPrice(orderform.getSinglePrice().multiply(new BigDecimal(orderform.getStockCount())));
        insert(orderform);

        shoppingCarService.delete(shoppingCarId);

        return orderform.getOrderNumber();
    }
}
