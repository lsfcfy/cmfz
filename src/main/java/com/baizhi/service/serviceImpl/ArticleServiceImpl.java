package com.baizhi.service.serviceImpl;

import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @ClearRedisCache
    @Override
    public void insert(Article article) {
        article.setId(UUID.randomUUID().toString().replace("-",""));
        article.setCreateDate(new Date());

        int i = articleDao.insertSelective(article);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
    }

    @ClearRedisCache
    @Override
    public void delete(String id) {
        Article article = articleDao.selectByPrimaryKey(id);
        int delete = articleDao.delete(article);
        if(delete == 0){
            throw new RuntimeException("删除失败");
        }
    }

    @ClearRedisCache
    @Override
    public void update(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if (i == 0) {
            throw new RuntimeException("更新失败");
        }
    }

    @RedisCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page-1) * rows, rows);
        List<Article> list = articleDao.selectByRowBounds(article,rowBounds);
        int i = articleDao.selectCount(article);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",i%rows== 0?i/rows:i/rows+1);
        map.put("records",i);
        return map;
    }
}
