package com.join.utils;

import com.join.vo.UserVo;

/**
 * @author join
 * @Description
 * @date 2022/8/30 20:42
 */
public class ThreadLocalUser {

    private ThreadLocalUser (){};

    private static final ThreadLocal<UserVo> LOCAL=new ThreadLocal<>();

    public static void put(UserVo userVo){
        LOCAL.set(userVo);
    }

    public static UserVo get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }

}
