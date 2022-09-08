package com.join.utils;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author join
 * @Description
 * @date 2022/9/7 15:32
 */
public class QiniuUtil {

    public static final String url = "http://rhsha4hoi.hb-bkt.clouddn.com";//个人域名
    private static final String accessKey = "1A9UEuMl_9pj8dsfsajkgnrGLlv7LmjbfRtESx";//密钥
    private static final String secretKey = "2Ry8w3Llhwcrosldfdsg1ccci3m-E0UxndyrCi";//密钥

    public Boolean upload(MultipartFile file, String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String bucket = "joinblog";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = null;

        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(uploadBytes, fileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //ignore
            return false;
        }
        return true;
    }
}
