package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by yyb on 3/10/19.
 */
public class RedisPool {
    private static JedisPool pool;//jedis lianjiechu
    private static Integer maxTotal=Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));
    private static Integer maxIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));
    private static Integer minIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));
    private static Boolean testOnBorrow=Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));
    private static Boolean testOnReturn=Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));

    private static String redisIp=PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort=Integer.parseInt(PropertiesUtil.getProperty("redis.port"));

    private static void initPool(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);

        pool=new JedisPool(config,redisIp,redisPort,1000*2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis=pool.getResource();
        jedis.set("yybkey","yybvale");
        returnResource(jedis);

        pool.destroy();
        System.out.println("end");
    }



}
