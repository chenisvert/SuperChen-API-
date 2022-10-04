package com.service_apis.client;

import com.atguigu.commonutils.Result;
import com.service_apis.client.lmpl.UserClientImpl;
import com.service_apis.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name = "service-user",fallback = UserClientImpl.class)
public interface userService {

//    public boolean isBuyCourse();

    @PostMapping( "/user/login")
    public String isLogin(@RequestBody User user);


}
