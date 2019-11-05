package com.baizhi.service.serviceImpl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
@Service("service")
@Transactional
public class AdminServiceImpl implements AdminService {
   @Autowired
   private AdminDao adminDao;
    @Override

    public void adminLogin(Admin admin, String vcode, HttpServletRequest request) {
        String code = (String) request.getSession().getAttribute("code");
        Admin admin1 = adminDao.selectOne(admin);

        if(code.equals(vcode)){
            if(admin.getName() != null){
                if(admin.getPassword().equals(admin1.getPassword())){
                    request.getSession().setAttribute("admin",admin1);
                }else{
                    throw new RuntimeException("密码错误");
                }
            }else{
                throw new RuntimeException("用户未注册");
            }
        }else{
          throw new RuntimeException("验证码错误");
        }



    }
}
