package com.jale.weblog.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jale.weblog.common.exception.WeblogException;
import com.jale.weblog.shopping.api.dataobject.Goods;
import com.jale.weblog.shopping.api.service.GoodsService;
import com.jale.weblog.user.api.dataobject.User;
import com.jale.weblog.user.api.service.UserService;
import com.jale.weblog.user.service.dao.UserMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@org.apache.dubbo.config.annotation.Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Reference
    private GoodsService goodsService;

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

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    //@Transactional
    @GlobalTransactional
    public void testSeata(String name) {
        User user = new User();
        user.setUserName(name);
        user.setPassword("123456");
        insert(user);

        Goods goods = new Goods();
        goods.setName(user.getUserName() + " 用户创建时自动生成的商品");
        goodsService.insert(goods);
    }
}
