package com.baizhi.service.serviceImpl;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("starService")
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;

    @Override
    public String insert(Star star) {
        star.setId(UUID.randomUUID().toString().replace("-",""));
        int i = starDao.insertSelective(star);
        if (i==0){
            throw new RuntimeException("添加失败");
        }
        return star.getId();
    }

    @Override
    public void delete(String id, HttpServletRequest request) {
        Star star = starDao.selectByPrimaryKey(id);
        int i = starDao.deleteByPrimaryKey(star);
        if(i==0){
            throw new RuntimeException("删除失败");
        }else{
            String photo = star.getPhoto();
            File file = new File(request.getServletContext().getRealPath("/image/"), photo);
            boolean delete = file.delete();
            if(delete == false){
                throw new RuntimeException("删除照片失败");
            }
        }

    }

    @Override
    public void update(Star star) {
        if("".equals(star.getPhoto())){
            star.setPhoto(null);
        }
        try {
            starDao.updateByPrimaryKeySelective(star);
        } catch (Exception e) {
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAllStar(Star star, Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Star> stars = starDao.selectByRowBounds(star, rowBounds);
        int count = starDao.selectCount(star);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",stars);
        map.put("records",count);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Star> queryAll() {
        List<Star> stars = starDao.selectAll();
        return stars;
    }
}
