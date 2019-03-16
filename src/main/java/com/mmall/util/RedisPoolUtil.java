package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

/**
 * Created by yyb on 3/10/19.
 */
@Slf4j
public class RedisPoolUtil {

    public static Long expire(String key,int exTime){
        Jedis jedis=null;
        Long result=null;

        try {
            jedis= RedisPool.getJedis();
            result=jedis.expire(key,exTime);
        }catch (Exception e){
            log.error("expire key:{}  error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }


    public static String setEx(String key,String value,int exTime){
        Jedis jedis=null;
        String result=null;

        try {
            jedis= RedisPool.getJedis();
            result=jedis.setex(key,exTime,value);
        }catch (Exception e){
            log.error("setex key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;

        }
        RedisPool.returnResource(jedis);
        return result;

    }



    public static String set(String key,String value){
        Jedis jedis=null;
        String result=null;

        try {
            jedis= RedisPool.getJedis();
            result=jedis.set(key,value);
        }catch (Exception e){
            log.error("set key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;

        }
      RedisPool.returnResource(jedis);
        return result;

    }

    public static String get(String key){
        Jedis jedis=null;
        String result=null;

        try {
            jedis= RedisPool.getJedis();
            result=jedis.get(key);
        }catch (Exception e){
            log.error("get kwy:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }


    public static Long del(String key){
        Jedis jedis=null;
        Long result=null;

        try {
            jedis= RedisPool.getJedis();
            result=jedis.del(key);
        }catch (Exception e){
            log.error("del key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }


    public static void main(String[] args) {
        Jedis jedis =RedisPool.getJedis();
        RedisPoolUtil.set("key1", "value1");
        String value=RedisPoolUtil.get("key1");

        RedisPoolUtil.setEx("keyex","valueex",60*10);

        RedisPoolUtil.expire("key1",20*60);
        RedisPoolUtil.del("key1");
        System.out.println("ened");
    }

}
