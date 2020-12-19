package com.jale.weblog.shopping.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jale.weblog.shopping.api.dataobject.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    Integer casUpdate(Map<String, Object> params);

}
