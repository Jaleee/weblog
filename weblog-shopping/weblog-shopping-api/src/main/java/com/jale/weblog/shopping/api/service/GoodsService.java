package com.jale.weblog.shopping.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jale.weblog.shopping.api.dataobject.Goods;
import com.jale.weblog.shopping.api.dto.GoodsSelectDto;

import java.util.List;

public interface GoodsService {

    IPage<Goods> selectPage(GoodsSelectDto goodsSelectDto);

    List<Goods> selectList(GoodsSelectDto goodsSelectDto);

    Goods insert(Goods goods);

    Goods selectById(Integer id);

    int deleteById(Integer id);

    Goods updateById(Goods goods);

}
