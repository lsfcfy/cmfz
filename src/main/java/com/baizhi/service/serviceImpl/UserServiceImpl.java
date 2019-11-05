package com.baizhi.service.serviceImpl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryUserByStarId(Integer page, Integer rows, String starId) {
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);

        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int i = userDao.selectCount(user);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("records",i);
        map.put("total",i%rows==0?i/rows:i/rows+1);
        return map;
    }

    //easypoi
    @Override
    public List<User> esp() {
        return userDao.selectAll();
    }

    //echarts
    @Override
    public List<User> queryUserBySxe(String sex) {

        return userDao.queryUserBySxe(sex);
    }
}
