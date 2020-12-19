package com.jale.weblog.shopping.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jale.weblog.common.exception.WeblogException;
import com.jale.weblog.shopping.api.dataobject.Goods;
import com.jale.weblog.shopping.api.dto.GoodsSelectDto;
import com.jale.weblog.shopping.api.service.GoodsService;
import com.jale.weblog.shopping.service.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@org.apache.dubbo.config.annotation.Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> selectList(GoodsSelectDto goodsSelectDto) {
        Map queryParams = JSON.parseObject(JSON.toJSONString(goodsSelectDto), Map.class);
        queryParams.remove("currentPage");
        queryParams.remove("pageSize");
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().allEq(queryParams);
        return goodsMapper.selectList(wrapper);
    }

    @Override
    public IPage<Goods>  selectPage(GoodsSelectDto goodsSelectDto) {
        IPage<Goods> pageParams = new Page<>(goodsSelectDto.getCurrentPage(), goodsSelectDto.getPageSize());
        Map queryParams = JSON.parseObject(JSON.toJSONString(goodsSelectDto), Map.class);
        queryParams.remove("currentPage");
        queryParams.remove("pageSize");
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().allEq(queryParams);
        IPage<Goods> pageData = goodsMapper.selectPage(pageParams, wrapper);
        return pageData;
    }

    @CachePut(value = "goods", key = "#goods.id")
    @Override
    public Goods insert(Goods goods) {
        goods.setAddTime(new Date());
        goodsMapper.insert(goods);

        if (goods.getName().contains("报错")) {
            int i = 1/0;
        }

        return selectById(goods.getId());
    }

    //@Cacheable(value = "goods", key = "#id")
    @Override
    public Goods selectById(Integer id) {
        return goodsMapper.selectById(id);
    }

    @CacheEvict(value = "goods", key = "#id")
    @Override
    public int deleteById(Integer id) {
        return goodsMapper.deleteById(id);
    }

    //@CachePut(value = "goods", key = "#goods.id")
    @Override
    public Goods updateById(Goods goods) {
        goods.setUpdateTime(new Date());
        goodsMapper.updateById(goods);
        return selectById(goods.getId());
    }

    @Override
    @Async
    public void testAsyncTask() {
        try {
            Thread.sleep(3000);
            System.out.println("异步任务执行成功了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer casUpdate(Map<String, Object> params) {
        return goodsMapper.casUpdate(params);
    }
}
