package com.example.superchen.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.example.superchen.anno.AccessLimit;
import com.example.superchen.anno.PermissionAnnotation;
import com.example.superchen.common.BaseContext;
import com.example.superchen.domain.dom.Access;
import com.example.superchen.domain.dom.Url;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.domain.ro.RoleEnum;
import com.example.superchen.service.UserService;
import com.example.superchen.utils.*;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.superchen.common.RedisKey.*;
import static com.example.superchen.domain.ro.ErrorCode.*;

@Slf4j
@Controller
@RequestMapping("/user")
@Transactional //开启事务
public class UserController extends BaseController {

    private Result result = new Result<>();

    private User user = new User();

    //随机数
    private Random random = new Random();


    @AccessLimit(seconds = 1, maxCount = 10)
    @RequestMapping(value = "/gomain")
    public String goLogin() throws IOException {
        try {
            User users = (User) session.getAttribute("login");

            if (users == null) {
                log.info("/user/gomin 检测到未登录");
                response.sendRedirect("../files/login/login.html");
            }
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            //根据User::getUsername 去查getPermission
            queryWrapper.eq(User::getUsername, users.getUsername());
            List<User> list = userService.list(queryWrapper);
            log.info(list.toString());
            //遍历集合
            List<User> collect = list.stream().map((item) -> {
                user.setPermission(item.getPermission());
                log.info(item.getPermission());
                return user;
            }).collect(Collectors.toList());
            log.info(user.getPermission());
            //普通用户跳转
            if (user.getPermission().equals("user")) {
                session.setAttribute("permission",RoleEnum.USER);
                return "user";
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
            session.setAttribute("permission",RoleEnum.ADMIN);
            return "admin/admin";
    }

    @AccessLimit(seconds = 2, maxCount = 3)
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User admin) throws IOException {

        User users = new User();

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Boolean login = userService.Login(admin);

        if (login) {
            //登录成功
            //根据前端传入的用户名，查询对应
            queryWrapper.select(User::getEmail, User::getPermission, User::getId, User::getCreateTime, User::getUsername,User::getToken);
            queryWrapper.eq(User::getUsername,admin.getUsername());
            List<User> list = userService.list(queryWrapper);
            //遍历集合
             list.stream().map((item) -> {
                //将遍历出来的放入User
                user.setUsername(admin.getUsername());
                user.setEmail(item.getEmail());
                user.setPermission(item.getPermission());
                user.setId(item.getId());
                user.setCreateTime(item.getCreateTime());
                user.setToken(item.getToken());
                users.setCreateTime(item.getCreateTime());
                return this.user;
            }).collect(Collectors.toList());
            users.setUsername(user.getUsername());
            users.setId(user.getId());
            users.setToken(user.getToken());
            //设置session
            session.setAttribute("login", users);
            //修改登录时间
            this.user.setCreateTime(LocalDateTime.now());
            userService.updateById(this.user);

            //放入ThreadLocal
            BaseContext.setCurrentId(this.user.getId());
            //生成token
            JwtUtils jwtUtils = new JwtUtils();
            String jwtToken = jwtUtils.getJwtToken(this.user.getId(), this.user.getUsername());
            //token放入redis
            redisTemplate.opsForValue().set(JWTTOKEN_KEY,jwtToken,12,TimeUnit.HOURS);
            //设置返回
            result.setCode(200);
            result.setMsg(jwtToken);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        //登陆失败
        result.setCode(LOGIN_ERROR.getErrCode());
        result.setMsg(LOGIN_ERROR.getErrMsg());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }
    @AccessLimit(seconds = 20, maxCount = 3) //15秒内 允许请求3次
    @ResponseBody
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        log.info("user = {}", user);
        String password = user.getPassword();
        password = MD5Util.getMD5(password);
        user.setPassword(password);
        String num = ValidateCodeUtils.generateValidateCode4String(5);
        String token = MD5Util.getMD5(num);
        user.setToken(token);
        try {
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("注册失败 错误：= {}", e.getMessage());
            result.setCode(500);
            result.setMsg("注册失败");
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        //删除用户列表的缓存
        redisTemplate.delete(USER_KET);
        result.setCode(200);
        result.setMsg("注册成功");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    @AccessLimit(seconds = 1, maxCount = 5)
    @ResponseBody
    @PostMapping("/repeat")
    public Result repeat(String username) {
        System.out.println(username);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        List<User> username1 = userService.list(queryWrapper);
        if (!username1.isEmpty()) {
            log.info("用户名存在");
            result.setCode(403);
            result.setMsg("用户名已经存在");
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        return null;

    }

    /*
     * 更加当前登录的用户获取其信息
     * */
    @PermissionAnnotation({RoleEnum.USER,RoleEnum.VIP})
    @ResponseBody
    @PostMapping("/getUser")
    public User getUser() {
        User user = (User) session.getAttribute("login");
        return user;
    }

    @AccessLimit(seconds = 1, maxCount = 5)
    @ResponseBody
    @GetMapping("/report")
    public Result report( Url url) {
        log.info(" /report 入参 ：{}",url);
        User user = (User) session.getAttribute("login");
        url.setCreateUser(user.getUsername());
        urlService.save(url);
        result.setCode(200);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg("投稿成功！");
        return result;
    }

    /*
    * 获取token
    * */
    @AccessLimit(seconds = 1, maxCount = 5)
    @ResponseBody
    @PostMapping("/geToken")
    public Result geToken() {
        //获取登录的用户名
        User users = (User) session.getAttribute("login");
        String username = users.getUsername();
        //根据用户名查询token
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //根据username 去找数据库匹配的用户名 查询所有
        queryWrapper.eq(User::getUsername,username);
        List<User> list = userService.list(queryWrapper);
        list.stream().map((item) ->{
            String token = item.getToken();
            user.setToken(token);
            return user;
        }).collect(Collectors.toList());
        result.setCode(200);
        result.setMsg(user.getToken());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }




    /***
     * 获取用户总数
     * @Author chen
     * @Date  8:23
     * @Param
     * @Return
     * @Since version-11

     */
    @AccessLimit(seconds = 1, maxCount = 6)
    @ResponseBody
    @GetMapping("/countRow")
    public Result countRow() {
        int count = userService.countRow();
        result.setCode(200);
        result.setMsg(String.valueOf(count));
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    @ResponseBody
    @GetMapping("/getState")
    public Result getState() {
        User user = (User) session.getAttribute("login");
        if (user == null){
            result.setCode(403);
            result.setMsg("未登录");
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        result.setCode(200);
        result.setMsg("已登录");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }


    @ResponseBody
    @GetMapping("/temporaryToken")
    public Result temporaryToken() {

        Object temporaryToken = redisTemplate.opsForValue().get(TOKEN_KEY);
        if (temporaryToken != null){
            Long expire = redisTemplate.opsForValue().getOperations().getExpire(TOKEN_KEY,TimeUnit.MINUTES);
            result.setCode(403);
            result.setMsg("临时token 已经被使用，请"+expire+"分钟后再申请！");
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        String token = ValidateCodeUtils.generateValidateCode4String(5);
        //设置token
        redisTemplate.opsForValue().set(TOKEN_KEY,token,1, TimeUnit.HOURS);
        result.setCode(200);
        result.setMsg("临时token 申请成功，有效期1小时 您的token："+token);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }



    /*
    * 以下为后台管理
    * */
    @AccessLimit(seconds = 10, maxCount = 3) //15秒内 允许请求3次
    @PermissionAnnotation(RoleEnum.ADMIN)
    @GetMapping("/list")
    public  String list() throws IOException {
        User user = (User) session.getAttribute("login");
        //判断权限

        List<User> cacheList = (List<User>) redisTemplate.opsForValue().get(USER_KET);

        if (cacheList != null){
            //回写页面
            log.info("[log]:命中用户列表缓存！");
            request.setAttribute("userList",cacheList);
            return "admin/diruser";
        }
        //查数据库，加缓存
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(User::getId);
        queryWrapper.select(User::getId,User::getUsername,User::getEmail,User::getPermission,User::getCreateTime,User::getToken);
        List<User> list = userService.list(queryWrapper);
        request.setAttribute("userList",list);
        //生成随机数
        int timeout = RandomUtils.nextInt(20);
        log.info("用户列表-缓存设置成功，时间："+timeout);
        redisTemplate.opsForValue().set(USER_KET,list,timeout,TimeUnit.HOURS);
        return "admin/diruser";
    }

    /***
     * 根据id删除用户
     * @Author chen
     * @Date  14:54
     * @Param
     * @Return
     * @Since version-11

     */
    @PermissionAnnotation(RoleEnum.ADMIN)
    @GetMapping("/delete/{id}")
    public  String delete(@PathVariable  Long id) throws IOException {
        User user = (User) session.getAttribute("login");
        //判断权限
        if (user.getPermission().equals("user")){
            response.sendRedirect("http://47.106.67.99/23");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        userService.remove(queryWrapper);
        return "admin/diruser";
    }

    /***
     * 开通访问量服务
     * @Author chen
     * @Date 23:37
     * @Param
     * @Return
     * @Since version-11

     */
    @ResponseBody
    @PostMapping("/openAccess")
    public Result openAccess() {

        User user = (User) session.getAttribute("login");

        LambdaQueryWrapper<User> queryWrapperUser = new LambdaQueryWrapper<>();
        Access access = new Access();
        //绑定session中的token
        access.setToken(user.getUsername());
        queryWrapperUser.eq(User::getToken,user.getUsername());
        List<User> listUser = userService.list(queryWrapperUser);

        LambdaQueryWrapper<Access> queryWrapperAccess = new LambdaQueryWrapper<>();
        queryWrapperAccess.eq(Access::getToken,user.getToken());

        log.info(user.getUsername());

        List<Access> list = accessService.list(queryWrapperAccess);
        access.setToken(user.getToken());
        if (list.size() != 0){
            result.setCode(400);
            result.setMsg("您已经开通了，请勿重复开通");
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        System.out.println(access);
        accessService.save(access);
        result.setCode(200);
        result.setMsg("开通成功");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }

    /***
     *
     * 退出登录
     * @Author chen
     * @Date  12:11
     * @Param
     * @Return Module
     * @Since version-11

     */

    @GetMapping("/exitLogin")
    public Module exitLogin() throws IOException {
        User login = (User) session.getAttribute("login");
        log.info("用户："+login.getUsername()+"退出登录");
        session.removeAttribute("login");
        response.sendRedirect("/user/gomain");
        return null;
    }


    /***
     * 反馈
     * @Author chen
     * @Date  19:08
     * @Param context
     * @Return Result
     * @Since version-11

     */
    @ResponseBody
    @GetMapping("/feedback/{context}")
    public Result feedback(@PathVariable String context){
        if (StringUtils.isEmpty(context)){
            result.setCode(PARAMS_ERROR.getErrCode());
            result.setMsg(PARAMS_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        }
        try {
            String response = HttpRequest.get("http://api.day.app/RAbJyhfhE9LUCM5zvkffcj/SuperChen-API-紧急通知/ip:"+IPUtil.getIpAddr(request)+"_消息："+context).body();
        }catch (Exception e){
            log.error(e.getMessage());
            result.setCode(SERVER_ERROR.getErrCode());
            result.setMsg(SERVER_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        result.setCode(200);
        result.setMsg("反馈成功");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    @AccessLimit(seconds = 1,maxCount = 2)
    @ResponseBody
    @PostMapping("/sendResetPasswordEmail")
    public Result sendResetPasswordEmail(@RequestBody User user){
        log.info("/user/sendResetPasswordEmail ===入参:{}",user);
        String username = user.getUsername();
        if (StringUtils.isEmpty(username)){
            result.setCode(PARAMS_ERROR.getErrCode());
            result.setMsg(PARAMS_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getUsername,User::getEmail);
        queryWrapper.eq(User::getUsername,username);
        List<User> list = userService.list(queryWrapper);
        if (list.isEmpty()){
            result.setCode(400);
            result.setMsg("用户未注册");
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        String codes = ValidateCodeUtils.generateValidateCode4String(5);
        String email = null;
        String subject = "【SuperChen-API】重置密码-验证码";
        String context=  "您的验证码是："+codes;
        for (User users:list) {
             email = users.getEmail();
        }
        userService.sendMsg(email,subject,context);
        //缓存2分钟
        redisTemplate.opsForValue().set(CODE_KEY+"_"+username,codes,2,TimeUnit.MINUTES);
        session.setAttribute("codeUsername",username);
        result.setCode(200);
        result.setMsg("验证码发送成功");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    /***
     *
     * 重置密码,跳转
     * @Author chen
     * @Date  19:08
     * @Param username
     * @Return Result
     * @Since version-11

     */
    @ResponseBody
    @GetMapping("/redirectPassword/{code}")
    public Result redirectPassword(@PathVariable String code) throws IOException {
        String username = (String) session.getAttribute("codeUsername");
        String cacheCode = (String) redisTemplate.opsForValue().get(CODE_KEY+"_"+username);
        if (code.equals(cacheCode)){
            String key = MD5Util.getMD5(code);
            redisTemplate.opsForValue().set(username+"_redirectPassword",key);
            result.setCode(200);
            result.setMsg("/user/goResetPassword/"+key);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        //返回
        result.setCode(CODE_ERROR.getErrCode());
        result.setMsg(CODE_ERROR.getErrMsg());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    /***
     *
     * 跳转修改密码
     * @Author chen
     * @Date  16:01
     * @Param
     * @Return
     * @Since version-11

     */

    @GetMapping("/goResetPassword/{key}")
    public String goResetPassword(@PathVariable String key) throws IOException {
        String username = (String) session.getAttribute("codeUsername");
        String keys  = (String) redisTemplate.opsForValue().get(username + "_redirectPassword");
        if (!keys.equals(key)){
            response.sendRedirect("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.tukuppt.com%2Fpng_preview%2F00%2F11%2F69%2F840wwqEs8Z.jpg%21%2Ffw%2F780&refer=http%3A%2F%2Fimg.tukuppt.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1664789659&t=fa31d76708dca34cde57a5e32a585e8d");
            return null;
        }
        return "resetPassword";

    }

    /***
     *
     * 修改密码
     * @Author chen
     * @Date  16:02
     * @Param
     * @Return
     * @Since version-11

     */
    @ResponseBody
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody User user){
        String password = user.getPassword();
        String username = (String) session.getAttribute("codeUsername");
        log.info("用户："+username+"___修改密码为:"+password);
        User userUpdate = new User();
        if (StringUtils.isEmpty(password)){
            result.setCode(PARAMS_ERROR.getErrCode());
            result.setMsg(PARAMS_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        List<User> list = userService.list(queryWrapper);
        //取出用户id
        for (User users:list) {
            userUpdate.setId(users.getId());
        }
        String md5 = MD5Util.getMD5(password);
        userUpdate.setPassword(md5);
        userService.updateById(userUpdate);

        result.setCode(200);
        result.setMsg("修改密码成功");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }


}
