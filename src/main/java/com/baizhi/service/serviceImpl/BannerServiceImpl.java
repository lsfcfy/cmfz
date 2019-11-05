package com.baizhi.service.serviceImpl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("banner")
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;


    @Override
    public String insert(Banner banner){
        banner.setId(UUID.randomUUID().toString().replace("-",""));
        banner.setCreateDate(new Date());

        int i = bannerDao.insertSelective(banner);
        if (i == 0){
            throw new RuntimeException("添加失败");
        }
        return banner.getId();
    }

    @Override
    public void delete(String id, HttpServletRequest request) {
        Banner banner = bannerDao.selectByPrimaryKey(id);
        int i = bannerDao.deleteByPrimaryKey(id);
        String cover1 = banner.getCover();
        if (i == 0) {
            throw new RuntimeException("删除失败");
        } else {
            String cover = banner.getCover();

            //获取时间转化后的文件夹
            String format = new SimpleDateFormat("yyyy-MM-dd").format(banner.getCreateDate());
            File file = new File(request.getServletContext().getRealPath("/banner/image/"+format+"/"),cover);
            boolean delete = file.delete();
            if (delete == false) {
                throw new RuntimeException("删除图片失败");
            }
        }
    }

    @Override
    public void update(Banner banner) {

        //如果图片没有修改，就不用设置图片
        if ("".equals(banner.getCover())){
            banner.setCover(null);
        }
        try {
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectAll(Banner banner,Integer page, Integer rows){
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Banner> list = bannerDao.selectByRowBounds(banner, rowBounds);
        int count = bannerDao.selectCount(banner);

        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页
        map.put("records",count);//总共有多少条数据

        return map;
    }
}
