package com.example.superchen.domain.dom;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ipaddress")
public class IpAddress {

    @TableId(type = IdType.AUTO)
    private int id;

    private String ip;

    private String address;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
