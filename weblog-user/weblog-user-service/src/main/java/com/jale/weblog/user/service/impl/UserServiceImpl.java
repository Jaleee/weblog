package com.jale.weblog.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jale.weblog.user.api.dataobject.User;
import com.jale.weblog.user.api.service.UserService;
import com.jale.weblog.user.service.dao.UserMapper;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@org.apache.dubbo.config.annotation.Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("user_name", user.getUserName())
                .eq("password", new Sha256Hash(user.getPassword()).toHex());
        User result = userMapper.selectOne(queryWrapper);

        return result;
    }

    @Override
    public User findByUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("user_name", userName);
        User result = userMapper.selectOne(queryWrapper);

        return result;
    }
}
