package com.jale.weblog.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jale.weblog.common.exception.WeblogException;
import com.jale.weblog.shopping.api.dataobject.Goods;
import com.jale.weblog.shopping.api.dataobject.Orderform;
import com.jale.weblog.shopping.api.service.GoodsService;
import com.jale.weblog.shopping.api.service.OrderformService;
import com.jale.weblog.user.api.dataobject.User;
import com.jale.weblog.user.api.dataobject.UserWallet;
import com.jale.weblog.user.api.service.UserService;
import com.jale.weblog.user.api.service.UserWalletService;
import com.jale.weblog.user.service.dao.UserMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@org.apache.dubbo.config.annotation.Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Reference(timeout = 5000)
    private GoodsService goodsService;

    @Reference
    private UserWalletService userWalletService;

    @Reference(timeout = 5000)
    private OrderformService orderformService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
            //try {
            if (i == 5) {
                throw new WeblogException("测试service方法内的异常");
            }
            System.out.println(i);
            /*} catch (Exception e) {
                System.out.println("捕获了异常:" + i);
            }*/
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

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public Integer order(Long userId, Long goodsId, Integer goodsCount) {
        Orderform orderform = null;

        UserWallet userWallet = userWalletService.getByUserId(userId);
        if (userWallet == null) {
            throw new WeblogException("用户不存在");
        }

        Goods goods = goodsService.selectById(goodsId.intValue());
        if (goods == null) {
            throw new WeblogException("商品不存在");
        }

        Boolean goodsLock = redisTemplate.opsForValue().setIfAbsent("goods:" + goods.getId(), goods.getId(), 5, TimeUnit.SECONDS);
        try {
            if (!goodsLock) {
                throw new WeblogException("业务繁忙，请稍后再试");
            }

            if (goods.getStockCount().compareTo(goodsCount) < 0) {
                throw new WeblogException("商品库存不足,库存数 " + goods.getStockCount() + ",需求数 " + goodsCount);
            }

            BigDecimal totalPrice = goods.getPrice().multiply(new BigDecimal(goodsCount).setScale(2));
            if (userWallet.getBalance().compareTo(totalPrice) < 0) {
                throw new WeblogException("用户余额不足");
            }

            goods.setStockCount(goods.getStockCount() - goodsCount);
            goodsService.updateById(goods);

            orderform = new Orderform();
            orderform.setOrderNumber((int) System.currentTimeMillis());
            orderform.setGoodsId(goods.getId());
            orderform.setGoodsName(goods.getName());
            orderform.setStockCount(goodsCount);
            orderform.setUserId(userId);
            orderform.setSinglePrice(goods.getPrice());
            orderform.setTotalPrice(totalPrice);
            orderformService.insert(orderform);

            userWallet.setBalance(userWallet.getBalance().subtract(totalPrice));
            userWalletService.update(userWallet);
        } finally {
            redisTemplate.delete("goods：" + goods.getId());
        }

        return orderform.getOrderNumber();
    }
}
