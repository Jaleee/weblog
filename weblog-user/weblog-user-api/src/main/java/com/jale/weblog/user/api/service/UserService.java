package com.jale.weblog.user.api.service;

import com.jale.weblog.user.api.dataobject.User;

public interface UserService {

    User login(User user);

    User findByUserName(String userName);

    void testServiceException();

    int insert(User user);

    void testSeata(String name);

    Integer order(Long userId, Long goodsId, Integer goodsCount);

}
