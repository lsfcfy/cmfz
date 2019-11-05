package com.baizhi.controller;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("star")
public class StarController {
    @Autowired
    private StarService starService;

    @RequestMapping("queryAll")
    public void queryAll(HttpServletResponse response) throws IOException {
        List<Star> list = starService.queryAll();

        //页面用dataUrl需要返回的格式   响应流
        String str = "<select>";
        for (Star star : list) {
            str += "<option value="+star.getId()+">"+star.getNickname()+"</option>";
        }
        str += "</select>";
        //防止响应乱码
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(str);
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(String oper, HttpServletRequest request,Star star){
        Map<String,Object> map = new HashMap<>();
        try {
            if (StringUtils.equals(oper,"add")){
                String id = starService.insert(star);
                map.put("message",id);
            }
            if (StringUtils.equals(oper,"del")){
                starService.delete(star.getId(),request);
            }
            if (StringUtils.equals(oper,"edit")){
                starService.update(star);
            }
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile photo,String id,HttpServletRequest request)  {
        Map<String, Object> map = new HashMap<>();

        try {
            photo.transferTo(new File(request.getServletContext().getRealPath("/image"),photo.getOriginalFilename()));
            Star star = new Star();
            star.setId(id);

            star.setPhoto(photo.getOriginalFilename());
            starService.update(star);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return  map;

    }
    @ResponseBody
    @RequestMapping("queryAllStar")
    public Map<String,Object> queryAllStar(Integer page, Integer rows, Star star){
        Map<String, Object> map = starService.queryAllStar(star,page,rows);
            return map;
    }
}
