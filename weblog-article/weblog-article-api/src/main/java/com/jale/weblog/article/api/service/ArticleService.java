package com.jale.weblog.article.api.service;

import com.jale.weblog.article.api.dataobject.Article;

import java.util.List;

public interface ArticleService {

    List<Article> selectList(Article article);

}
