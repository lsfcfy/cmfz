package com.baizhi.service;

import com.baizhi.entity.Star;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface StarService {
    //为了返回id 添加图片
    String insert(Star star);

    void delete(String id, HttpServletRequest request);

    void update(Star star);
    //分页查询所有
    Map<String,Object> queryAllStar(Star star,Integer page, Integer rows);

    //专辑列表添加时显示明星昵称
    List<Star> queryAll();
}
