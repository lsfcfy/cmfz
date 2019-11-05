package com.baizhi.controller;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.StrictMath.random;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;


    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, HttpServletRequest request,Album album){
        Map<String,Object> map = new HashMap<>();

        try {
            if (StringUtils.equals(oper,"add")){
                String id = albumService.insert(album);
                map.put("message",id);
            }
            if (StringUtils.equals(oper,"del")){
                albumService.delete(album.getId(),request);
            }
            if (StringUtils.equals(oper,"edit")){
                albumService.update(album);
            }
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message",e.getMessage());
            map.put("status",false);
        }
        return map;
    }

    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile cover, String id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        try {
            //文件上传
            cover.transferTo(new File(request.getServletContext().getRealPath("/image"),cover.getOriginalFilename()));

            //修改album对象中的cover
            Album album = new Album();
            album.setId(id);
            album.setCover(cover.getOriginalFilename());
            albumService.update(album);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return  map;
    }
        @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Map<String, Object> map = albumService.queryAllAlbum(page, rows);
        return map;
    }
}
