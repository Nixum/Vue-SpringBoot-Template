package com.nixum.common.constant;

public class RedisConstant {

    // 缓存过期时间一个月即30*24小时
    public static final int EXPIRE_ONEMONTH = 2592000;     //60*60*24*30
    // 缓存过期时间十天即10*24小时
    public static final int EXPIRE_TENDAY = 864000;        //60*60*24*10
    // 过期时间为一周即7*24小时
    public static int EXPIRE_ONEWEEK = 604800;      //7*60*60*24
    // 缓存过期时间一天即24小时
    public static final int EXPIRE_ONEDAY = 86400;         //60*60*24
    // 缓存过期时间半天即12小时
    public static final int EXPIRE_HALFDAY = 43200;        //60*60*12
    // 缓存过期时间三小时即3*60分钟
    public static final int EXPIRE_THREEHOUR = 10800;      //3*60*60
    // 缓存过期时间一小时即60分钟
    public static final int EXPIRE_ONEHOUR = 3600;         //60*60
    // 缓存过期时间半小时即30分钟
    public static final int EXPIRE_HALFHOUR = 1800;        //60*30
    // 缓存过期时间十分钟
    public static final int EXPIRE_TENMINUTE = 600;        //60*10
    // 缓存过期时间90秒
    public static final int EXPIRE_NINETY = 90;
    // 缓存过期时间半分钟即30秒
    public static final int EXPIRE_HALFMINUTE = 30;
}
