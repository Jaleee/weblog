package com.jale.weblog.shopping.service.impl;

import com.jale.weblog.common.exception.WeblogException;
import com.jale.weblog.shopping.api.dataobject.Goods;
import com.jale.weblog.shopping.api.dataobject.ShoppingCar;
import com.jale.weblog.shopping.api.service.GoodsService;
import com.jale.weblog.shopping.api.service.ShoppingCarService;
import com.jale.weblog.shopping.service.mapper.ShoppingCarMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@org.apache.dubbo.config.annotation.Service
public class ShoppingCarServiceImpl implements ShoppingCarService {

    @Reference
    private GoodsService goodsService;

    @Autowired
    private ShoppingCarMapper shoppingCarMapper;

    @Override
    public int insert(ShoppingCar shoppingCar) {
        shoppingCar.setAddTime(new Date());
        return shoppingCarMapper.insert(shoppingCar);
    }

    @Override
    public int update(ShoppingCar shoppingCar) {
        shoppingCar.setUpdateTime(new Date());
        return shoppingCarMapper.updateById(shoppingCar);
    }

    @Override
    public ShoppingCar findById(Integer id) {
        return shoppingCarMapper.selectById(id);
    }

    @Override
    @Transactional
    public void saveToShoppingCar(Long userId, Integer goodsId, Integer goodsCount) {
        Goods goods = goodsService.selectById(goodsId);
        if (goods == null) {
            throw new WeblogException("商品不存在");
        }

        if (goodsCount.compareTo(goods.getStockCount()) > 0) {
            throw new WeblogException("库存不足");
        }

        ShoppingCar shoppingCar = new ShoppingCar();
        shoppingCar.setUserId(userId);
        shoppingCar.setGoodsId(goodsId);
        shoppingCar.setGoodsName(goods.getName());
        shoppingCar.setStockCount(goodsCount);
        insert(shoppingCar);
    }

    @Override
    public int delete(Integer id) {
        return shoppingCarMapper.deleteById(id);
    }
}
