package com.baizhi.controller;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.ObjectView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("edit")
    public Map<String,Object> edit(Banner banner,String oper,HttpServletRequest request) throws IOException {

        Map<String,Object> map = new HashMap<>();

        try {
            if (StringUtils.equals(oper,"add")){
                String id = bannerService.insert(banner);
                map.put("message",id);
            }
            if (StringUtils.equals(oper,"del")){
                bannerService.delete(banner.getId(),request);
            }
            if (StringUtils.equals(oper,"edit")) {
                bannerService.update(banner);
            }
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            //状态和信息存入map
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile cover,String id,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();

       //文件相对路径获取绝对
        String realPath = request.getServletContext().getRealPath("/banner/image");
        //减少文件负担，方便查找,设置一个时间文件夹
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
       // File file1 = new File(realPath+"/"+format);
        File file = new File(realPath,format);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            //文件上传
            cover.transferTo(new File(file,cover.getOriginalFilename()));
            //因为添加时没有上传相关图片，现在添加即改变banner对象cover属性
            //根据添加时前台传来的id，获取图片信息
            Banner banner = new Banner();
            banner.setId(id);
            banner.setCover(cover.getOriginalFilename());
            bannerService.update(banner);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
    @RequestMapping("queryAll")
    public Map<String, Object> selectAll(Banner banner,Integer page, Integer rows) {
        Map<String, Object> map = bannerService.selectAll(banner,page, rows);
        return map;

    }
}
