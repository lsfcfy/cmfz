package com.baizhi.service;

import com.baizhi.entity.Article;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.reactive.AbstractReactiveTransactionManager;

import java.util.Map;

public interface ArticleService {
    void insert(Article article);

    void delete(String id);

    void update(Article article);

    Map<String, Object> queryAll(Integer page,Integer rows);
}
