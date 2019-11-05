package com.baizhi.service;

import com.baizhi.entity.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> queryUserByStarId(Integer page,Integer rows,String starId);

    //easypoi
    List<User> esp();

    //echarts
    List<User> queryUserBySxe(String sex);
}
