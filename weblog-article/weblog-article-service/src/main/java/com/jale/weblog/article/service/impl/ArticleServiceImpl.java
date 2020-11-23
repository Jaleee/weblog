package com.jale.weblog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jale.weblog.article.service.dao.ArticleMapper;
import com.jale.weblog.article.api.dataobject.Article;
import com.jale.weblog.article.api.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@org.apache.dubbo.config.annotation.Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> selectList(Article article) {
        return articleMapper.selectList(new QueryWrapper<>(article));
    }

}
