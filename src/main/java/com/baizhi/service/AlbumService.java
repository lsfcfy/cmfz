package com.baizhi.service;

import com.baizhi.entity.Album;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AlbumService {
    String insert(Album album);

    void delete(String id, HttpServletRequest request);

    void update(Album album);

    //添加章节时需要指定属于哪个专辑
    Album queryOne(String id);

    Map<String, Object> queryAll(Integer page, Integer rows);
}
