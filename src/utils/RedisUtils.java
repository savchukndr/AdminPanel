package utils;

import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.io.FileWriter;
import java.io.IOException;
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

    public List<String> getKeyList(){
        Set<String> keys = jedis.keys("employee:*");
        List<String> list = new ArrayList<>(keys);
        Collections.sort(list);
        return list;
    }

    JSONObject generateJsonObject(List<String> keyList){
        JSONObject json = new JSONObject();
        for(int i=0;i<=keyList.size() - 1;i++) {
            Map<String, String> mapOfValues = getKeyMap(keyList.get(i));
            try {
                json.put(keyList.get(i), mapOfValues);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
