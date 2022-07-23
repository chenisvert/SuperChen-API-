package com.example.superchen.domain.ro;

import lombok.Data;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Result<T> implements Serializable {
    private Integer code;
    private T msg;
    private String date;
}