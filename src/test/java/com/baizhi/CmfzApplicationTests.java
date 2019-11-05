package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import com.baizhi.service.AdminService;
import com.baizhi.service.AlbumService;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes=CmfzApplication.class)
public class CmfzApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminService adminService;
    @Autowired
    private BannerDao bannerDao;

    //
    @Autowired
    private UserDao userDao;


    @Test
    public void pub() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-e891702436bc4f33b60b17739844b6b2");
        goEasy.publish("test-channel","kkkkkkkkkkkkkkkkkkkkkkkkkkkk");
    }

    @Test
    public void testUser(){
        List<User> list = userDao.queryUserBySxe("男");
        System.out.println(list);
    }


    @Test
    void contextLoads() {
//        Admin admin = new Admin();
//      //  admin.setName("ls");
//        Admin admin1 = adminDao.selectOne(admin);
//        System.out.println(admin1);
        System.out.println("\\");


    }


    @Test
    void testBanner() {
        Banner banner = new Banner();
        banner.setId("2");
        banner.setName("lsd");
        banner.setStatus("1");
        banner.setCreateDate(new Date());
        bannerDao.updateByPrimaryKey(banner);
    }

    @Autowired
    private AlbumService albumService;
    @Test
    public void test12(){
        Album album = new Album();
        album.setId("1");
        album.setCount(1);

        albumService.update(album);
        System.out.println("over");
    }

    @Autowired
    private AlbumDao albumDao;
    @Test
    public void test13(){
        Album album = new Album();
        album.setId("2");
        album.setCount(1);
//        int i = albumDao.updateByPrimaryKeySelective(album);
//        System.out.println(i);

//        List<Album> list = albumDao.select(album);
//        for (Album a : list) {
//            System.out.println(a);
//        }
        System.out.println(album+"1111111111111111111111111");
        int i = albumDao.updateByPrimaryKeySelective(album);
        System.out.println(i);


    }

}
