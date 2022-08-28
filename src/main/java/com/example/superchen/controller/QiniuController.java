package com.example.superchen.controller;


import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

import static com.example.superchen.domain.ro.ErrorCode.PARAMS_ERROR;


@Slf4j
@RestController
@RequestMapping("/qiniu")
public class QiniuController extends BaseController{

    private Result result = new Result<>();

    @GetMapping("/getQnyFile/{fileName}")
    public Module getWeather( @PathVariable String fileName)  {

        String fileUrl = qiniuProvider.download(fileName);
        try {
            response.sendRedirect(fileUrl);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("详细错误信息：{}",e.getMessage());
        }
        return null;
    }

    @PostMapping("/uploadFile")
    public Result uploadFile( @PathVariable MultipartFile file)  {
        //上传文件为空
        if (file.isEmpty()){
            result.setCode(PARAMS_ERROR.getErrCode());
            result.setMsg(PARAMS_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        try {
            byte [] byteArr = file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);

            //获得原始文件名
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //使用UUID重新生存文件名，防止覆盖
            String fileName = UUID.randomUUID().toString()+suffix;
                String upload = qiniuProvider.upload(inputStream,fileName);
            inputStream.close();
            result.setCode(200);
            result.setMsg(upload);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();

        }finally {

        }
        return null;
    }
}
