package com.baizhi.controller;

import com.baizhi.utils.CreateValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
@Controller
@RequestMapping("code")
public class ImageCodeController {
    @RequestMapping("getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreateValidateCode createValidateCode = new CreateValidateCode();
        String code = createValidateCode.getCode();
        request.getSession().setAttribute("code",code);
        //响应画图
        ServletOutputStream s = response.getOutputStream();
       createValidateCode.write(s);

    }

}
