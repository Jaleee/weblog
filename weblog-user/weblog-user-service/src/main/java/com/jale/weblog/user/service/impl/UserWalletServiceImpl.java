package com.jale.weblog.user.service.impl;

import com.jale.weblog.user.api.dataobject.UserWallet;
import com.jale.weblog.user.api.service.UserWalletService;
import com.jale.weblog.user.service.dao.UserWalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@org.apache.dubbo.config.annotation.Service
public class UserWalletServiceImpl implements UserWalletService {

    @Autowired
    private UserWalletMapper userWalletMapper;

    @Override
    public UserWallet getByUserId(Long userId) {
        return userWalletMapper.selectById(userId);
    }

    @Override
    public int update(UserWallet userWallet) {
        return userWalletMapper.updateById(userWallet);
    }

}
