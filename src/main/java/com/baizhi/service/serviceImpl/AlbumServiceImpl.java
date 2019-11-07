package com.baizhi.service.serviceImpl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

import static java.lang.StrictMath.random;

@Service("AlbumService")
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private StarDao starDao;


    @Override
    public String insert(Album album) {
        album.setId(UUID.randomUUID().toString().replace("-",""));
        album.setCreateDate(new Date());
        album.setCount(0);
        int i = albumDao.insertSelective(album);

        if (i==0){
            throw new RuntimeException("添加失败");
        }
        return album.getId();
    }

    @Override
    public void delete(String id, HttpServletRequest request) {

        Album album = albumDao.selectByPrimaryKey(id);
        int i = albumDao.deleteByPrimaryKey(album);
        if (i == 0){
            throw new RuntimeException("删除失败");
        }else{
            String cover = album.getCover();
            File file = new File(request.getServletContext().getRealPath("/image/"),cover);
            boolean delete = file.delete();
            if (delete == false) {
                throw new RuntimeException("删除图片失败");
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void update(Album album) {
        if ("".equals(album.getCover())){
            album.setCover(null);
        }
        System.out.println(album);;
        int i = albumDao.updateByPrimaryKeySelective(album);
        if(i==0){
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album queryOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Album> list = albumDao.selectByRowBounds(album,rowBounds);

        //因为专辑是属于某个明星，所以根据starId查找到一条明星数据
        //页面需要根据这条数据查到相应的属性
        for (Album a : list) {
           Star star = starDao.selectByPrimaryKey(a.getStarId());
           a.setStar(star);
     }
        int i = albumDao.selectCount(album);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("records",i);
        map.put("total",i%rows==0?i/rows:i/rows+1);
        return map;
    }
}
