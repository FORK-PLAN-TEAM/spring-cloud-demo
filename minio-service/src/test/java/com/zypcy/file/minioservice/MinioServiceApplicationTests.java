package com.zypcy.file.minioservice;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.zypcy.file.minioservice.service.MinioService;
import com.zypcy.file.minioservice.util.FileContentTypeUtils;
import io.minio.ObjectStat;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Tags;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
class MinioServiceApplicationTests {

    @Autowired
    private MinioService minioService;
    @Autowired
    private WebApplicationContext context;

    private String bucketName = "test";

    @Test
    void contextLoads() {
    }

    //是否存在桶
    @Test
    public void bucketExists() {
        Assert.assertTrue(minioService.bucketExists(bucketName));
    }

    //创建桶
    @Test
    public void makeBucket() {
        Assert.assertTrue(minioService.makeBucket("xnxx"));
    }

    //删除桶
    @Test
    public void removeBucket() {
        Assert.assertTrue(minioService.removeBucket("xnxx"));
    }

    //列出所有存储桶名称
    @Test
    public void listBucketNames() {
        minioService.listBucketNames().forEach(System.out::println);
    }

    //列出所有存储桶
    @Test
    public void listBuckets() {
        Assert.assertNotNull(minioService.listBuckets());
    }

    //列出存储桶中的所有对象名称
    @Test
    public void listObjectNames() {
        minioService.listObjectNames(bucketName).forEach(System.out::println);
    }

    //列出存储桶中的所有对象
    @Test
    public void listObjects() {
        minioService.listObjects(bucketName).forEach(itemResult -> {
            try {
                System.out.println(itemResult.get().objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //列出存储桶中的所有对象，根据前缀、后缀、数量
    @Test
    public void listObjectsQuery() {
        minioService.listObjects(bucketName, "2020", null, 0).forEach(itemResult -> {
            try {
                System.out.println(itemResult.get().objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //删除对象tag信息
    @Test
    public void deleteObjectTags() {
        minioService.deleteObjectTags(bucketName, "202010300940.txt");
    }

    //删除文件
    @Test
    public void removeObject() {
        Assert.assertTrue(minioService.removeObject(bucketName, "75b6bbbf5db44e9ea1e5e9d469123316.txt"));
    }

    //上传文件
    @Test
    public void putObject1() throws IOException {
        String path = "classpath:static/files/rocketmq.txt";
        String suffix = path.substring(path.lastIndexOf("."));
        String contentType = FileContentTypeUtils.getContentType(suffix);
        BufferedInputStream bis = FileUtil.getInputStream(path);
        if (bis != null && bis.available() > 0) {
            //String objectName = IdUtil.simpleUUID() + suffix;
            String objectName = "75b6bbbf5db44e9ea1e5e9d469123316" + suffix;
            minioService.putObject(bucketName, objectName, bis , contentType);
        }
    }

    //获取文件流
    @Test
    public void getObject() {
        InputStream is = minioService.getObject(bucketName, "75b6bbbf5db44e9ea1e5e9d469123316.txt");
        Assert.assertNotNull(is);
    }

    //给文件设置tags
    @Test
    public void setObjectTags() {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("name", "zhuyu");
        map.put("time", LocalDateTime.now().toString());
        minioService.setObjectTags(bucketName, "75b6bbbf5db44e9ea1e5e9d469123316.txt", map);
    }

    //获取文件tags
    @Test
    public void getObjectTags() {
        Tags tags = minioService.getObjectTags(bucketName, "202011/c191a36defe3434c945a47ab697b3e0f.png");
        Assert.assertNotNull(tags);
        System.out.println(tags.get());
    }

    //生成一个给HTTP GET请求用的presigned URL。
    @Test
    public void getPresignedObjectUrl(){
        String url = minioService.getPresignedObjectUrl(bucketName , "75b6bbbf5db44e9ea1e5e9d469123316.txt" , 3 * 24 * 60 * 60 , Method.GET);
        Assert.assertNotNull(url);
        System.out.println(url);
        //http://106.52.16.101:9000/test/75b6bbbf5db44e9ea1e5e9d469123316.txt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=xnxx%2F20201030%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20201030T030014Z&X-Amz-Expires=259200&X-Amz-SignedHeaders=host&X-Amz-Signature=ee58079bf78bee9b0bf5a5fd807a3533fe1d5c525616f8fd7df7544274385822
    }

    //获取对象的元数据
    @Test
    public void statObject(){
        ObjectStat objectStat = minioService.statObject(bucketName, "75b6bbbf5db44e9ea1e5e9d469123316.txt");
        Assert.assertNotNull(objectStat);
        System.out.println(objectStat);
    }

    //文件访问路径
    @Test
    public void getObjectUrl(){
        System.out.println(minioService.getObjectUrl(bucketName , "75b6bbbf5db44e9ea1e5e9d469123316.txt"));
        //http://106.52.16.101:9000/test/75b6bbbf5db44e9ea1e5e9d469123316.txt
    }

    //下载文件，在项目根目录
    @Test
    public void downloadObject(){
        String objectName =  "75b6bbbf5db44e9ea1e5e9d469123316.txt";
        minioService.downloadObject(bucketName , objectName , objectName);
    }

    //下载文件
    @Test
    public void downloadObject2(){
        HttpServletResponse response =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String objectName =  "75b6bbbf5db44e9ea1e5e9d469123316.txt";
        minioService.downloadObject(bucketName , objectName , response);
    }

}
