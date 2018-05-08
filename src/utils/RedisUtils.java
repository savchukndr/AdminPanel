package utils;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by Andrii Savchuk on 08.05.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class RedisUtils {

    private Jedis jedis;

    public RedisUtils(){
        jedis = new Jedis("localhost");
    }

    public Map<String, String> getKeyMap(String key){
        return jedis.hgetAll(key);
    }

//    public static void main(String args[]){
////        Jedis jedis = new Jedis("localhost");
////
////        System.out.println(jedis.hgetAll("employee:4"));
//        Jedis jedis = new Jedis("localhost");
//        Set<String> keys = jedis.keys("employee:*");
//        List<String> list = new ArrayList<>(keys);
//        Collections.sort(list);
//        HashMap<Integer, String> listWithId= new HashMap<>();
//        int i = 0;
//        while(i != list.size()){
//            listWithId.put(i, list.get(i));
//            i++;
//        }
//        System.out.println(listWithId);
//    }
}
