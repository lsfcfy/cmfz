package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface BannerService {
    //增 为了jquery页面添加图片 返回id
    String insert(Banner banner) ;
    //删
    void delete(String id, HttpServletRequest request);
    //改
    void update(Banner banner);

    //根据当前页和当前行数查所有
   // List<Banner> queryAll(Banner banner,Integer page,Integer rows);
    Map<String, Object> selectAll(Banner banner,Integer page, Integer rows);
}
