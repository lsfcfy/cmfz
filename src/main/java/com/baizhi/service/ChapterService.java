package com.baizhi.service;

import com.baizhi.entity.Chapter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ChapterService {
    String insert(Chapter chapter);

    void delete(String id, HttpServletRequest request);

    void update(Chapter chapter);

    Map<String,Object> queryAll(Integer page, Integer rows, String albumId);
}
