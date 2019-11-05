package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("insert")
    public String insert(Article article){
        articleService.insert(article);
        return "back/article";
    }

    @RequestMapping("delete")
    public String delete(String id){
        articleService.delete(id);
        return "back/article";
    }

    @RequestMapping("update")
    public String update(Article article){
        articleService.update(article);
        return "back/article";
    }

    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile articleImg, HttpServletRequest request)  {
        Map<String, Object> map = new HashMap<>();
        try {
            String realPath = request.getServletContext().getRealPath("/image/");
            File file = new File(realPath, articleImg.getOriginalFilename());
            articleImg.transferTo(file);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
 return  map;

    }


    @ResponseBody
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page, Integer rows){
        return articleService.queryAll(page, rows);
    }
}
