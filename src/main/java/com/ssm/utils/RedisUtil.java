package com.ssm.utils;

import net.sf.json.JSONArray;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Redis的工具类  包括Redis的set和get方法  还有json数组和list集合之间转换的方法
 * Created by ssm on 2017/4/5.
 */
public class RedisUtil {

    private static RedisTemplate redisTemplate;
    private static JedisConnectionFactory jedisConnectionFactory;
//    private static JedisPoolConfig jedisPoolConfig;

    static {
        if (redisTemplate == null) {
            redisTemplate = SpringUtils.getBean(RedisTemplate.class);
        }
        if (jedisConnectionFactory == null) {
            jedisConnectionFactory = SpringUtils.getBean(JedisConnectionFactory.class);
        }
//        if (jedisPoolConfig == null) {
//            jedisPoolConfig = SpringUtils.getBean(JedisPoolConfig.class);
//        }
    }

    /**
     * 从redis中获取数据再转换成list集合方法
     * @param convertModel  要转换的实体类型
     * @param cacheKey  redis中缓存数据的key值
     * @param <T>   传入的实体类泛型
     * @return  返回list集合
     */
    public static <T> List<T> getCacheResult(Class<T> convertModel, String cacheKey) {

        List<T> userList = null;
        //从redis中按照cacheKey查询数据
        String result = getCache(cacheKey);
        //把获得的数据进行转换成json数组
        JSONArray data = JSONArray.fromObject(result);

        //判断获取从redis中获取的数据是否为空,如果不为空则进行转换成集合类型
        if (result != null) {
//            userList = ToolJson.jsonToList(result,convertModel);
        }
        return userList;
    }

    /**
     * 把值放入Redis方法
     * @param setList 需要存放的值
     * @return 返回0失败, 返回1成功
     */
    public static int setValue(String redisKey, List setList) {

        if (setList != null) {

            //把集合转换成字符串
            String cacheValue = ToolJson.modelToJson(setList);
            //把得到的数据放入到redis中
            redisTemplate.opsForValue().set(redisKey, cacheValue);
            return 1;
        }
        return 0;
    }

    /**
     * 按照key值取出对应的缓存值
     * @param getCacheKey 获取缓存值的key
     * @return 返回json对象
     */
    public static String getCache(String getCacheKey) {

        try {
            //从redis中按照key值获取数据
            return redisTemplate.opsForValue().get(getCacheKey).toString();
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }
}
