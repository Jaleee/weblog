package com.jale.weblog.shopping.api.service;

import com.jale.weblog.shopping.api.dataobject.Orderform;

public interface OrderformService {

    void insert(Orderform orderform);

    Integer generateOrder(Integer shoppingCarId);

}
