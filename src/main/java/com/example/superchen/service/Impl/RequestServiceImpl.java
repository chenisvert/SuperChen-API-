package com.example.superchen.service.Impl;

import com.example.superchen.service.RequestService;
import com.example.superchen.utils.DateUtils;
import com.example.superchen.utils.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {







    /***
     * 发送https
     * @Author chen
     * @Date  13:41
     * @Param
     * @Return String
     * @Since version-11

     */

    @Override
    public String https(String urls) throws Exception {
        //线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        URL url = null;
        url = new URL("https://"+urls);
        String html = request(url);
        URL finalUrl = url;
        FutureTask futureTask1 = new FutureTask<>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                String html = request(finalUrl);
                return html;
            }

        });

        Thread thread = new Thread(futureTask1,"t2");
        pool.execute(thread);
        String htmls = (String) futureTask1.get();

        TextUtil textUtil = new TextUtil();
        String finalHtml = htmls;
        //开线程
        FutureTask futureTask = new FutureTask<>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                //提取中文
                String bodytext = textUtil.getChinese(finalHtml);
                //返回一个 Integer 类型的
                return bodytext;
            }

        });

        Thread threads = new Thread(futureTask,"t3");
        pool.execute(threads);


        String text = (String) futureTask.get();
        pool.shutdown();
        return text;

    }

    @Override
    public String http(String urls) throws Exception {
        //线程池
        ExecutorService pool = Executors.newFixedThreadPool(3);

        URL url = null;

        url = new URL("http://"+urls);


        String html = request(url);

        TextUtil textUtil = new TextUtil();
        log.info("网址： {}",url);
        String date = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
//        String body = HttpRequest.get("http://"+url).body();
        String finalHtml = html;
        FutureTask futureTask = new FutureTask<>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                //提取中文
                String bodytext = textUtil.getChinese(finalHtml);
                //返回一个 Integer 类型的
                return bodytext;
            }

        });

        Thread thread = new Thread(futureTask,"t3");
        pool.execute(thread);


        String text = (String) futureTask.get();
        pool.shutdown();
        return text;
    }

    @Override
    public String request(URL url) throws Exception {

        //2、 获取连接对象
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        //3、 设置连接信息
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        // 4、获取数据
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String html="";
        while((line=bufferedReader.readLine())!=null){
            html+=line+"\n";
        }

        // 5、关闭资源
        inputStream.close();
        bufferedReader.close();
        return html;
    }
}
