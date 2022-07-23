package com.example.superchen.domain.dom;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("mp4url")
public class Url  {

    @TableId(type = IdType.AUTO)
    private int id;

    private String url;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    private int state;

    private int sisk; //推送的人数

    private String remark; //备注
}
