package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface UserDao extends Mapper<User> {
    //若用echarts不能用通用mapper，因为条件自定义多样
    List<User> queryUserBySxe(String sex);
}
