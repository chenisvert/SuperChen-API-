package com.example.superchen.domain.dom;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("access")
public class Access {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer count; //访问量

    private boolean warning; //是否达到预警阈值

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //预警阈值
    private Integer threshold;

    private String token;
}
