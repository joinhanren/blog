package com.join.utils;

import java.util.Random;

/**
 * @author join
 * @Description
 * @date 2022/8/31 9:45
 */
public class VerifyInfoUtil {

    /**
     * 创建动态验证码
     * @return
     */

    public static String createVerifyCode(){
        Random random=new Random();
        int randomNumber= random.nextInt(1000000);
        String verifyCode= String.format("%06d",randomNumber);//格式化随机数字符串，生成0-999999随机数，不足6位补0
        return verifyCode;
    }

    /**
     * 创建验证信息
     * @return
     */

    public static String createVerifyInfo(String code){
        return "您的动态口令是: "+code+
                "。您正在注册账户，[2] 分钟有效，该验证码仅用于身份验证，请勿泄露给其他人使用!";
    }

}
