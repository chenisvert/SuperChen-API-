package com.example.superchen.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.superchen.domain.dom.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器
 */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {


    /**
     * 插入操作，自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]... metaObject = {}",metaObject);
        log.info(metaObject.toString());
        //填充时间
        metaObject.setValue("createTime", LocalDateTime.now());
//        metaObject.setValue("updateTime",LocalDateTime.now());
//        metaObject.setValue("createUser",user.getUsername());
//        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    /**
     * 更新操作，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]... metaObject = {}",metaObject);
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);

        metaObject.setValue("updateTime",LocalDateTime.now());
//        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
