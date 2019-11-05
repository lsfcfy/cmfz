package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page, Integer rows,String starId){
        Map<String, Object> map = userService.queryUserByStarId(page, rows, starId);
        return map;
    }


    //echarts
    @RequestMapping("echarts")
    public List<User> echarts(String sex){
        List<User> users = userService.queryUserBySxe(sex);

        return users;

    }
    //easypoi
    @RequestMapping("esp")
    public void esp(HttpServletResponse response){
        //准备数据
        List<User> esp = userService.esp();

        //创建workbook
        Workbook workbook = ExcelExportUtil.exportExcel
                (new ExportParams("持明法州用户", "用户"),
                        com.baizhi.entity.User.class, esp);
        //下载附件的文件名
        String fileName = "用户表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        try {
            //处理下载时中文名乱码
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
           //设置请求头
            response.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
