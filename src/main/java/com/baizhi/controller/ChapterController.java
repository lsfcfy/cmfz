package com.baizhi.controller;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, HttpServletRequest request,Chapter chapter){
        Map<String,Object> map = new HashMap<>();
        try {
            if (StringUtils.equals(oper,"add")){
                String id = chapterService.insert(chapter);
                map.put("message",id);
            }
            if(StringUtils.equals(oper,"del")){
                //chapterService.delete(chapter.getId(),request);
            }
            if (StringUtils.equals(oper,"edit")){
              //  chapterService.update(chapter);
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
    public Map<String,Object> upload(MultipartFile title,HttpServletRequest request,String id,String albumId){
            Map<String,Object> map = new HashMap<>();

        try {
            //文件上传
            File file = new File(request.getServletContext().getRealPath("/player/"),title.getOriginalFilename());
            title.transferTo(file);

            //修改文件名称
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setTitle(title.getOriginalFilename());
            //文件大小 初始字节单位
            BigDecimal size = new BigDecimal(title.getSize());
            BigDecimal mal = new BigDecimal(1024);
            //参数1：保留两位小数，参数2：四舍五入
            BigDecimal realSize = size.divide(mal).divide(mal).setScale(2, BigDecimal.ROUND_HALF_UP);
            chapter.setSize(realSize+"MB");
            Encoder encoder = new Encoder();
            //编译文件信息 获取文件时长 初为毫秒
            long duration = encoder.getInfo(file).getDuration();
            chapter.setDuration(duration/1000/60+":"+duration/1000%60);
            chapterService.update(chapter);
            //修改专辑中的数量
            Album album = albumService.queryOne(albumId);
            album.setCount(album.getCount()+1);
            albumService.update(album);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
             map.put("status",false);
             map.put("message",e.getMessage());
        }
    return map;
    }
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows,String albumId){
        return chapterService.queryAll(page,rows,albumId);
    }
}
