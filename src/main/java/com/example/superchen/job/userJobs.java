package com.example.superchen.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.controller.BaseController;
import com.example.superchen.domain.dom.Access;
import com.example.superchen.service.AccessService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/***
 *
 * 定时任务配置类
 * @Author chen
 * @Date  13:18
 * @Param
 * @Return
 * @Since version-11

 */
@Slf4j
@Configuration    // 1. 代表当前类是一个配置类
@EnableScheduling // 2.开启定时任务
public class userJobs extends BaseController {

    /***
     *
     * 每天1点清空访问量
     * @Author chen
     * @Date  13:18
     * @Param
     * @Return
     * @Since version-11

     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanAccess() {
        LambdaQueryWrapper<Access> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Access::getCleanday, 1);
        //判断是否大于0
        queryWrapper.gt(Access::getCount, 0);
        List<Access> list = accessService.list(queryWrapper);
//        Access access = null;
        List<Access> listNew = new ArrayList<>();
        for (Access access : list) {
            System.out.println(access);
            access.setCount(0);
            listNew.add(access);
        }
        //判空
        if (!listNew.isEmpty()) {
            accessService.updateBatchById(listNew);
            log.info("============访问量清空成功！");
        }
        log.info("============没有需要清空的访问量！");
    }

}
