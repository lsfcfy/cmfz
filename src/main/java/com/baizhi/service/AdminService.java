package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    void adminLogin(Admin admin, String vcode, HttpServletRequest request);
}
