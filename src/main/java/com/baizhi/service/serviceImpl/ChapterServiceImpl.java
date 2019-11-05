package com.baizhi.service.serviceImpl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("chapterService")
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    public String insert(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString().replace("-",""));
        chapter.setCreateDate(new Date());

        int i = chapterDao.insertSelective(chapter);
        if (i==0){
            throw new RuntimeException("添加章节失败");
        }
        return chapter.getId();
    }

    @Override
    public void delete(String id, HttpServletRequest request) {

    }

    @Override
    public void update(Chapter chapter) {
        int i = chapterDao.updateByPrimaryKeySelective(chapter);

        if (i==0){
            throw new RuntimeException("修改章节失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows, String albumId) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);

        List<Chapter> list = chapterDao.selectByRowBounds(chapter, rowBounds);
        int i = chapterDao.selectCount(chapter);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("records",i);
        map.put("total",i%rows==0?i/rows:i/rows+1);
        return map;
    }



}
