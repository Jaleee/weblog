package com.jale.weblog.shopping.api.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jale.weblog.shopping.api.dataobject.ShoppingCar;
import io.swagger.models.auth.In;

public interface ShoppingCarService {

    int insert(ShoppingCar shoppingCar);

    int update(ShoppingCar shoppingCar);

    ShoppingCar findById(Integer id);

    void saveToShoppingCar(Long userId, Integer goodsId, Integer goodsCount);

    int delete(Integer id);

}
