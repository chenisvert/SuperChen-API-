package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.anno.AccessLimit;
import com.example.superchen.api.BaiduAddressUtil;
import com.example.superchen.api.getText;
import com.example.superchen.domain.dom.IpAddress;
import com.example.superchen.domain.dom.Url;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import com.example.superchen.utils.GenerateCodeUtils;
import com.example.superchen.utils.IPUtil;
import com.example.superchen.utils.IpAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.superchen.common.RedisKey.TOKEN_KEY;
import static com.example.superchen.domain.ro.ErrorCode.TOKEN_ERROR;

/***
 * 开放 api
 * @Author chen
 * @Date  23:37
 * @Param
 * @Return
 * @Since version-11

 */

@Slf4j
@RestController
@RequestMapping("/apis")
public class PublicApiController extends BaseController {


    private Result result = new Result<>();

    /***
     * 随机或者指定id获取图片
     * @Author chen
     * @Date 23:37
     * @Param
     * @Return
     * @Since version-11

     */

    @AccessLimit(seconds = 1, maxCount = 5)
    @ResponseBody
    @GetMapping("/getimages/{id}/{token}")
    public Result Imgurl(@PathVariable int id, @PathVariable String token) {
        log.info("入参 id:{},token：{}", id, token);
        Object tockens = redisTemplate.opsForValue().get(TOKEN_KEY);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getToken, token);
        List<User> userList = userService.list(queryWrapper);
        System.out.println(userList);
        //认证临时token
        if (token.equals(tockens)) {
            log.info("临时token: {}", tockens);
            Result result = pubicApiService.getImage(id, token);
            return result;
        }
        //认证token
        if (userList.size() != 0) {
            Result result = pubicApiService.getImage(id, token);
            return result;
        }
        //临时token 和 token 都认证失败
        result.setCode(403);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg("token 认证错误");
        return result;
    }

    /***
     *
     * 可以直接用img标签引入
     * @Author chen
     * @Date 10:48
     * @Param
     * @Return
     * @Since version-11

     */

    @AccessLimit(seconds = 1, maxCount = 7)
    @GetMapping({"/img/{token}"})
    public ModelAndView getImg(@PathVariable String token) throws IOException {
        log.info("入参  token：{}", token);
//        Object tockens = this.redisTemplate.opsForValue().get("token");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getToken, token);
        List<User> userList = this.userService.list(queryWrapper);
        System.out.println(userList);
        if (userList.size() != 0) {
            Result result = this.pubicApiService.getImage(-1, token);
            System.out.println(result.getMsg());
            //跳转
            response.sendRedirect((String) result.getMsg());
            return null;
        }
        log.info("token 认证错误");
        //跳转到认证失败的图片
        response.sendRedirect("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fmmbiz.qpic.cn%2Fmmbiz_png%2FdtcgBqxJpLJLLiaQBFAbyrkbG1jZqiasEn71RLC0PEjoOIArSx1LnNc5j3khj6a2ZfEE0HVnZ9ib1WCLI05RJ21Cw%2F0%3Fwx_fmt%3Dpng.jpg&refer=http%3A%2F%2Fmmbiz.qpic.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661742790&t=c3dc9ac61144ec85c54e7367380fe033");
        return null;
    }


    /***
     *
     * 二维码生成接口
     * @Author chen
     * @Date 11:59
     * @Param
     * @Return
     * @Since version-11

     */

    @AccessLimit(seconds = 1, maxCount = 3)
    @ResponseBody
    @GetMapping({"/generateCode/{url}/{token}"})
    public Result generateCode(@PathVariable String url, @PathVariable String token) throws IOException {
        log.info("入参  token：{}", token);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getToken, token);
        List<User> userList = this.userService.list(queryWrapper);
        if (userList.size() != 0) {
            //跳转
            try {
                String fileName = GenerateCodeUtils.getCode(url);
                result.setCode(200);
                result.setMsg(fileName);
                result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        result.setCode(403);
        result.setMsg("token错误！");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }


    /***
     *
     * 回显二维码
     * @Author chen
     * @Date 13:24
     * @Param
     * @Return
     * @Since version-11

     */
    //回显图片
    @ResponseBody
    @GetMapping("/downloadCode/{name}")
    public void download(@PathVariable String name) {
        try {
            //输入流，通过输入流读取文件内容
            String path = "/codeimages/";
            FileInputStream fileInputStream = new FileInputStream(new File(path + name + ".jpg"));


            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("图片回显失败");
        }
    }

    /***
     *
     * 二维码跳转
     * @Author chen
     * @Date  18:16
     * @Param
     * @Return
     * @Since version-11

     */
    @ResponseBody
    @GetMapping("/generateCode/{token}")
    public ModelAndView generateCode(@PathVariable String token) throws IOException {
        log.info("入参  token：{}", token);
//        Object tockens = this.redisTemplate.opsForValue().get("token");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getToken,token);
        List<User> userList = this.userService.list(queryWrapper);
        System.out.println(userList);
        if (userList.size() != 0) {
            Result result = pubicApiService.getImage(-1, token);
            System.out.println(result.getMsg());
            //跳转
            response.sendRedirect((String) result.getMsg());
            return null;
        }
        log.info("token 认证错误");
        //跳转到认证失败的图片
        response.sendRedirect("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fmmbiz.qpic.cn%2Fmmbiz_png%2FdtcgBqxJpLJLLiaQBFAbyrkbG1jZqiasEn71RLC0PEjoOIArSx1LnNc5j3khj6a2ZfEE0HVnZ9ib1WCLI05RJ21Cw%2F0%3Fwx_fmt%3Dpng.jpg&refer=http%3A%2F%2Fmmbiz.qpic.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661742790&t=c3dc9ac61144ec85c54e7367380fe033");
        return null;

    }

    /***
     *
     * 跳转视频
     * @Author chen
     * @Date  19:52
     * @Param
     * @Return
     * @Since version-11
     * @return

     */

    @GetMapping("/getMyUserMp4/{token}")
    public Result getMyUserMp4(@PathVariable String token) throws IOException {

        log.info("入参  token：{}", token);
//        Object tockens = this.redisTemplate.opsForValue().get("token");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getToken,token);
        List<User> userList = this.userService.list(queryWrapper);
        System.out.println(userList);
        if (userList.size() != 0) {
            Url url1 = new Url();
            int countRow = urlService.countRow();
            Random random = new Random();
            int i = random.nextInt(countRow + 1);
            if (i == 0) {
                i = i + 1;
            }
            System.out.println(i);
            LambdaQueryWrapper<Url> queryWrappers = new LambdaQueryWrapper<Url>();
            queryWrappers.eq(Url::getState, 1);
            queryWrappers.eq(Url::getId, i);
            List<Url> list = urlService.list(queryWrappers);
            System.out.println(list);
            //遍历获取url
            list.stream().map((item) -> {
                String url = item.getUrl();
                url1.setUrl(url);
                return url1;
            }).collect(Collectors.toList());
            response.sendRedirect((String) url1.getUrl());
            return null;
        }

        result.setCode(TOKEN_ERROR.getErrCode());
        result.setMsg(TOKEN_ERROR.getErrMsg());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }

    @GetMapping("/getText/{token}")
    public Result getText( @PathVariable String token) throws IOException, InterruptedException {

        log.info("入参  token：{}", token);
//        Object tockens = this.redisTemplate.opsForValue().get("token");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getToken,token);
        List<User> userList = userService.list(queryWrapper);
        System.out.println(userList);
        if (userList.size() != 0) {
            String texts = new getText().getTexts();
            result.setCode(200);
            result.setMsg(texts);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        log.info("token 认证错误");
        result.setCode(TOKEN_ERROR.getErrCode());
        result.setMsg(TOKEN_ERROR.getErrMsg());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }

    /***
     *
     * 根据ip获取地理位置
     * @Author chen
     * @Date  21:49
     * @Param
     * @Return
     * @Since version-11

     */

    @AccessLimit(seconds = 1, maxCount = 8)
    @GetMapping("/getAddress/{token}")
    public Object getWz(@PathVariable String token ) {
        BaiduAddressUtil baiduAddressUtil = new BaiduAddressUtil();
        IpAddress addresssIp = new IpAddress();
        int timeOut = Integer.parseInt(RandomStringUtils.randomNumeric(2));
        String ipAddr = IPUtil.getIpAddr(request);
        log.info("入参  token：{}", token);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        LambdaQueryWrapper<IpAddress> queryWrapperip = new LambdaQueryWrapper();
        queryWrapper.eq(User::getToken,token);
        List<User> userList = userService.list(queryWrapper);
        System.out.println(userList);
        if (userList.size() != 0) {
            log.info("认证成功！");
            //加入缓存
            String addressCaChe = (String) redisTemplate.opsForValue().get(ipAddr);
            //第一层（缓存查询返回）
            if (addressCaChe != null){
                log.info("命中缓存：getAddress");
                result.setCode(200);
                result.setMsg(addressCaChe);
                result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
                return result;
            }
            log.info("无缓存命中——getAddress");


            String address1 = IpAddressUtils.getIpSource(ipAddr);
            if (!StringUtils.isEmpty(address1)){
                log.info("第三方ip库文件命中！！！");
                result.setCode(200);
                result.setMsg(address1);
                result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
                return result;
            }

            queryWrapperip.eq(IpAddress::getIp,ipAddr);
            List<IpAddress> list = ipAddressService.list(queryWrapperip);
            //第二层（ip数据库查询返回）
            if (!list.isEmpty()){
                //数据库查到了
                log.info("ip数据库命中！");
                //遍历集合
                list.stream().map((item) -> {
                    addresssIp.setAddress(item.getAddress());
                    return addresssIp;
                }).collect(Collectors.toList());
                //查到数据库放入缓存
                redisTemplate.opsForValue().set(ipAddr,addresssIp.getAddress(),timeOut,TimeUnit.HOURS);
                result.setCode(200);
                result.setMsg(addresssIp.getAddress());
                result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
                return result;
            }

            log.info(ipAddr);
            //第三层（第三方接口返回）
            String address = baiduAddressUtil.getThirdAddress(ipAddr);
            System.out.println(address);

            //新ip 放入ip数据库
            addresssIp.setIp(ipAddr);
            addresssIp.setAddress(address);
            ipAddressService.save(addresssIp);
            result.setCode(200);
            result.setMsg(address);
            //新地址缓存
            redisTemplate.opsForValue().set(ipAddr,address,timeOut,TimeUnit.HOURS);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        result.setCode(TOKEN_ERROR.getErrCode());
        result.setMsg(TOKEN_ERROR.getErrMsg());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }





}
