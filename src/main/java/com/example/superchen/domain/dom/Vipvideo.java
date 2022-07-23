package com.example.superchen.domain.dom;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("vipvideo")
@Data
public class Vipvideo {

    @TableId(type = IdType.AUTO)
    private int id;

    private String url;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

}
