package com.jale.weblog.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jale.weblog.common.exception.WeblogException;
import com.jale.weblog.user.api.dataobject.User;
import com.jale.weblog.user.api.service.UserService;
import com.jale.weblog.user.service.dao.UserMapper;
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
                .eq("password", user.getPassword());
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

    @Override
    public void testServiceException() {
        for (int i = 0; i < 10; i++) {
            try {
                if (i == 5) {
                    throw new WeblogException("测试service方法内的异常");
                }
                System.out.println(i);
            } catch (Exception e) {
                System.out.println("捕获了异常:" + i);
            }
        }

    }

}
