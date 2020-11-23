package com.jale.weblog.article.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jale.weblog.article.api.dataobject.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
