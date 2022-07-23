package com.example.superchen.domain.dom;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("imagesurl")
public class ImagesUrl {

    @TableId(type = IdType.AUTO)
    private int id;

    private String url;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String type; //图片类型

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    private int state;


    private String bz; //备注
}
