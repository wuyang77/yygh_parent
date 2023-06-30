package com.wuyang.yygh.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wuyang.yygh.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestOss {
    @Resource
    private ConstantPropertiesUtil constantPropertiesUtil;
    @Test
    public String testFileUpload(MultipartFile file){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = constantPropertiesUtil.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = constantPropertiesUtil.getKeyid();
        String accessKeySecret = constantPropertiesUtil.getKeysecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = constantPropertiesUtil.getBucketname();

        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();//这里生成的UUID中间带有4个_需要替换掉
        String timeUrl = new DateTime().toString("yyyy/MM/dd");
        System.out.println(timeUrl);
        objectName = timeUrl+"/"+objectName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //创建输入流
            InputStream inputStream = file.getInputStream();
            //将断点和访问KeyId,访问keysecret放入OSSClient实例
            //创建putobject请求
            ossClient.putObject(bucketName,objectName,inputStream);
            //https://wuyang77.oss-cn-beijing.aliyuncs.com/%E9%9B%AA%E5%A4%A9%20%E6%A0%91%E6%9E%97%20%E5%B8%85%E5%93%A5%20%D0%A4%D5%BD%20%E5%9B%B4%E5%B7%BE%204k%20%E9%AB%98%E6%B8%85%20%E6%89%8B%E6%9C%BA%E5%A3%81%E7%BA%B8_%E5%BD%BC%E5%B2%B8%E5%9B%BE%E7%BD%91.jpg
            return "https://"+bucketName+"."+endpoint+"/"+objectName;
        }catch (Exception ce) {
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
