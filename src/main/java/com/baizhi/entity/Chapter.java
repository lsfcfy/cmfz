package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.omg.PortableServer.ServantActivator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 章节实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class Chapter implements Serializable {
    @Id
    private String id;
    private String title;
    private String singer;  //歌手
    private String size;  //大小
    private String duration; //时长
    @JSONField(format = "yyyy-MM-dd HH:mm:ss:SSS")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private String albumId;
}
