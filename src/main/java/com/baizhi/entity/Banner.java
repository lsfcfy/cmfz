package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Banner implements Serializable{
    @Id
    private String id;
    private String name;
    private String cover;
    private String description;
    private String status;
    //处理时差 @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")//指定控制@responseBody转换对象时属性日期类型格式
    @DateTimeFormat(pattern = "yyyy-MM-dd") //转换
    private Date createDate;
}
