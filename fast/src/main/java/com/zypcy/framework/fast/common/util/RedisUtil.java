package com.zypcy.framework.fast.common.util;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.zypcy.framework.fast.common.config.SpringContextApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtil {

    /** 缓存生存时间, 默认一周 */
    private static final int expire = 7 * 24 * 60 * 60;
    private static StringRedisTemplate redisTemplate = SpringContextApplication.getBean(StringRedisTemplate.class);

    //private Keys KEYS = new Keys();
    //private Strings STRING = new Strings();
    //private Lists LIST = new Lists();
    //private Hash HASH = new Hash();
    //private Sets SET = new Sets();
    //private SortSet SORTSET = new SortSet();

    public static class Strings {

        /**
         * 根据key获取记录
         * @param key 要获取的Key值
         * @return 值
         */
        public static String get(String key) {
            return redisTemplate.opsForValue().get(key);
        }

        /**
         * 根据key获取记录
         * @param clazz 值的类型
         * @param key   要获取的Key值
         * @return 值
         */
        public static <T> T get(Class<T> clazz, String key) {
            String value = redisTemplate.opsForValue().get(key);
            return JSON.parseObject(value, clazz);
        }

        /**
         * 将值value关联到key，并将key的生存时间设为seconds
         * @param key     要设置的Key
         * @param seconds 过期时间，以秒为单位
         * @param value   要设置的值
         * @return 成功返回true，否则返回false
         */
        public static void setExpire(String key, int seconds, String value) {;
            redisTemplate.opsForValue().set(key , value , seconds , TimeUnit.SECONDS);
        }

        /**
         * 将值value关联到key，并将key的生存时间设为seconds
         * @param key     要设置的Key
         * @param seconds 过期时间，以秒为单位
         * @param value   要设置的值
         * @return 成功返回true，否则返回false
         */
        public static <T> void setExpire(String key, int seconds, T value) {
            redisTemplate.opsForValue().set(key , JSON.toJSONString(value) , seconds , TimeUnit.SECONDS);
        }

        /**
         * 添加一条记录，前提是KEY之前不存在
         * @param key   要设置的Key
         * @param value 要设置的值
         * @return 成功返回true，否则返回false
         */
        public static boolean setIfAbsent(String key, String value) {
            return redisTemplate.opsForValue().setIfAbsent(key , value);
        }

        /**
         * 添加一条记录，前提是KEY之前不存在
         * @param key   要设置的Key
         * @param value 要设置的值
         * @return 成功返回true，否则返回false
         */
        public static <T> boolean setIfAbsent(String key, T value) {
            return redisTemplate.opsForValue().setIfAbsent(key , JSON.toJSONString(value));
        }

        /**
         * 添加记录, 如果记录已存在将覆盖原有的value
         * @param key   要设置的Key
         * @param value 要设置的值
         * @return 成功返回true，否则返回false
         */
        public static void set(String key, String value) {
            redisTemplate.opsForValue().set(key, value);
        }

        /**
         * 添加记录, 如果记录已存在将覆盖原有的value
         * @param key   要设置的Key
         * @param value 要设置的值
         * @return 成功返回true，否则返回false
         */
        public static <T> void set(String key, T value) {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(value));
        }

        /**
         * 只针对vlue为字符串，在指定的key中追加value
         * @param key   要设置的Key
         * @param value 要追加的值
         * @return 追加后value的长度
         */
        public static long append(String key, String value) {
            return redisTemplate.opsForValue().append(key , value);
        }

        /**
         * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
         * @param key    要设置的Key
         * @param number 要减去的值
         * @return 减值后的值
         */
        public static long decrement(String key, long number) {
            return redisTemplate.opsForValue().decrement(key , number);
        }

        /**
         * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
         * @param key    要设置的Key
         * @param number 要增加的值
         * @return 相加后的值
         */
        public static long increment(String key, long number) {
            return redisTemplate.opsForValue().increment(key, number);
        }

        /**
         * 只针对vlue为字符串，获得KEY对应的字符串里指定范围的子串
         * @param key         要设置的Key
         * @param startOffset 开始位置(包含)
         * @param endOffset   结束位置(包含)
         * @return 截取的值
         */
        public static String getRange(String key, long startOffset, long endOffset) {
            return redisTemplate.opsForValue().get(key, startOffset, endOffset);
        }

        /**
         * 设置KEY对应的VALUE，并返回老的VALUE<br/>
         * 如果key存在返回之前的value,否则返回null
         * @param key   要设置的Key
         * @param value 要设置的新值
         * @return 原始value或null
         */
        public static String getAndSet(String key, String value) {
            return redisTemplate.opsForValue().getAndSet(key, value);
        }

        /**
         * 设置KEY对应的VALUE，并返回老的VALUE<br/>
         * 如果key存在返回之前的value,否则返回null
         * @param clazz 值的类型
         * @param key   要设置的Key
         * @param value 要设置的新值
         * @return 原始value或null
         */
        public static <T> T getAndSet(Class<T> clazz, String key, T value) {
            String str = getAndSet(key, JSON.toJSONString(value));
            if (str == null || str.isEmpty())
                return null;
            else{
                return (T) JSON.parseObject(str, clazz);
            }
        }

        /**
         * 获取多个key的值, 如果指定的key不存在，则返回List的对应位置将是null
         * @param keys 要设置的Key
         * @return 多个Key所对应的值集合
         */
        public static List<String> multiSet(List<String> keys) {
            return redisTemplate.opsForValue().multiGet(keys);
        }

        /**
         * 获取多个key的值, 如果指定的key不存在，则返回List的对应位置将是null
         * @param clazz 值的类型
         * @param keys   要设置的Key
         * @return 多个Key所对应的值集合
         */
        public static <T> List<T> multiSet(Class<T> clazz, List<String> keys) {
            List<String> list = redisTemplate.opsForValue().multiGet(keys);
            List<T> newList = new ArrayList<T>();
            for (String s : list) {
                if (s != null && !s.isEmpty()) {
                    Type type = new TypeToken<List<T>>(){}.getType();
                    newList.add((T) JSON.parseObject(s, type));
                }
                else {
                    newList.add(null);
                }
            }
            return newList;
        }

        /**
         * 批量设置多个key-value, 只针对value为简单字符串（不包含逗号和引号）<br/>
         * MSET 是一个原子操作, 要么全部写入成功，要么全部没有写入<br/>
         * 因为无法判断是否写入成功，因此使用后要进行exsist的判断
         * @param map 例:keysvalues="key1","value1","key2","value2";
         * @return
         */
        public static void multiSet(Map<String, String> map) {
            redisTemplate.opsForValue().multiSet(map);
        }
    }

    // ////////////////////////////////////////////////////////
    // List
    // 所有关于列表的操作
    // ////////////////////////////////////////////////////////
    public static class Lists {
        /**
         * List大小
         * @param key 要查询的Key
         * @return list的大小
         */
        public static long size(String key) {
            return redisTemplate.opsForList().size(key);
        }

        /**
         * 设置LIST索引为INDEX的元素的值
         * @param key 要操作的key
         * @param index 位置
         * @param value 值
         * @return 成功返回true，否则返回false
         */
        public static void set(String key, int index, String value) {
            redisTemplate.opsForList().set(key , index , value);
        }

        /**
         * 设置LIST索引为INDEX的元素的值
         * @param key 要操作的key
         * @param index 位置
         * @param value 值
         * @return 成功返回true，否则返回false
         */
        public static <T> void set(String key, int index, T value) {
            redisTemplate.opsForList().set(key , index , JSON.toJSONString(value));
        }

        /**
         * 设置List到指定的key
         * @param key
         * @param value
         * @return
         */
        public static <T> void set(String key,List<T> value){
            for(int i=0;i<value.size();i++){
                set(key,i,value.get(i));
            }
        }

        /**
         * 获取List中指定位置的值
         * @param key 要查询的key
         * @param index 位置
         * @return 指定位置的值
         */
        public static String index(String key, int index) {
            return redisTemplate.opsForList().index(key , index);
        }

        /**
         * 获取List中指定位置的值
         * @param clazz 值的类型
         * @param key 要查询的key
         * @param index 位置
         * @return 指定位置的值
         */
        public static <T> T index(Class<T> clazz, String key, int index) {
            String value = redisTemplate.opsForList().index(key , index);
            if (value != null && !value.isEmpty()) {
                return JSON.parseObject(value, clazz);
            }
            else {
                return null;
            }
        }

        /**
         * 获取数据的全部list
         * @param clazz
         * @param key
         * @return
         */
        public static <T> List<T> getList(Class<T> clazz,String key){
            List<T> result = new ArrayList<>();
            List<String> list = redisTemplate.opsForList().range(key ,0 , -1);
            for (String str: list) {
                result.add(JSON.parseObject(str , clazz));
            }
            return result;
        }

        /**
         * 获取数据的全部list string类型
         * @param key
         * @return
         */
        public static List<String> getList(String key){
            long size = size(key);
            List<String> list = new ArrayList<String>();
            for(long i=0;i<size;i++){
                String t = index(key,Integer.parseInt(String.valueOf(i)));
                list.add(t);
            }
            return list;
        }

        /**
         * 删除并取得LIST左边一个元素
         * @param key 要操作的key
         * @return 移出的记录
         */
        public static String leftPop(String key) {
            return redisTemplate.opsForList().leftPop(key);
        }

        /**
         * 删除并取得LIST左边一个元素
         * @param clazz 值的类型
         * @param key 要操作的key
         * @return 移出的记录
         */
        public static <T> T leftPop(Class<T> clazz, String key) {
            String value = redisTemplate.opsForList().leftPop(key);
            if (value != null && !value.isEmpty()) {
                return JSON.parseObject(value, clazz);
            }
            else {
                return null;
            }
        }

        /**
         * 从任务队列key中获取一个任务，并将该任务放入暂存队列tempKey
         * @param clazz
         * @param key	原始key
         * @param tempKey 目标key
         * @return
         */
        public static <T> T rightPopAndLeftPush(Class<T> clazz,String key,String tempKey){
            String value = redisTemplate.opsForList().rightPopAndLeftPush(key , tempKey);
            if (value != null && !value.isEmpty())
                return JSON.parseObject(value, clazz);
            else
                return null;
        }

        /**
         * 删除并取得LIST右边一个元素
         * @param key 要操作的key
         * @return 移出的记录
         */
        public static String rightPop(String key) {
            return redisTemplate.opsForList().rightPop(key);
        }

        /**
         * 删除并取得LIST右边一个元素
         * @param clazz 值的类型
         * @param key 要操作的key
         * @return 移出的记录
         */
        public static <T> T rightPop(Class<T> clazz, String key) {
            String value = redisTemplate.opsForList().rightPop(key);
            if (value != null && !value.isEmpty())
                return JSON.parseObject(value, clazz);
            else
                return null;
        }

        /**
         * 在LIST左边添加一个或多个元素
         * @param key 要操作的key
         * @param value 要添加的记录
         * @return 记录总数
         */
        public static long leftPushAll(String key, String... value) {
            long count = redisTemplate.opsForList().leftPushAll(key , value);
            return count;
        }

        /**
         * 在LIST左边添加一个或多个元素
         * @param key 要操作的key
         * @param value 要添加的记录
         * @return 记录总数
         */
        public static long leftPushAll(String key, List<String> value) {
            long count = redisTemplate.opsForList().leftPushAll(key, value);
            return count;
        }

        /**
         * 在LIST左边添加一个元素
         * @param key 要操作的key
         * @param value  要添加的记录
         * @return 记录总数
         */
        public static <T> long leftPush(String key, T value) {
            long count = redisTemplate.opsForList().leftPush(key, JSON.toJSONString(value));
            return count;
        }


        /**
         * 在LIST右边添加一个或多个元素
         * @param key  要操作的key
         * @param value 要添加的记录
         * @return 记录总数
         */
        public static long rightPushAll(String key, String... value) {
            return redisTemplate.opsForList().rightPushAll(key, value);
        }

        /**
         * 在LIST右边添加一个或多个元素
         * @param key 要操作的key
         * @param value 要添加的记录
         * @return 记录总数
         */
        public static long rightPushAll(String key, List<String> value) {
            return redisTemplate.opsForList().rightPushAll(key, value);
        }

        /**
         * 在LIST左边添加一个或多个元素
         * @param key 要操作的key
         * @param value 要添加的记录
         * @return 记录总数
         */
        public static <T> long rightPushAll(Class<T> clazz, String key, List<T> value) {
            int valueSize = value.size();
            if (valueSize == 0)
                return 0;

            String[] tmp = new String[valueSize];
            for (int i = 0; i < valueSize; i++)
                tmp[i] = JSON.toJSONString(value.get(i));
            long count = redisTemplate.opsForList().rightPushAll(key, tmp);
            return count;
        }

        /**
         * 在LIST右边添加一个元素
         * @param key 要操作的key
         * @param value 要添加的记录
         * @return 记录总数
         */
        public static <T> long rightPush(String key, T value) {
            return redisTemplate.opsForList().rightPush(key, JSON.toJSONString(value));
        }

        /**
         * 获取指定范围的记录，可以做为分页使用
         * @param key 要操作的key
         * @param start 起始位置
         * @param end 结束位置
         * @return 指定范围的记录
         */
        public static List<String> range(String key, long start, long end) {
            List<String> list = redisTemplate.opsForList().range(key, start, end);
            return list;
        }

        /**
         * 获取指定范围的记录，可以做为分页使用
         * @param clazz 值的类型
         * @param key 要操作的key
         * @param start 起始位置
         * @param end 结束位置
         * @return 指定范围的记录
         */
        public static <T> List<T> range(Class<T> clazz, String key, long start, long end) {
            List<String> list = redisTemplate.opsForList().range(key, start, end);
            List<T> newList = new ArrayList<T>();
            Type type = new TypeToken<List<T>>(){}.getType();
            for (String s : list) {
                if (s != null && !s.isEmpty()) {
                    newList.add((T) JSON.parseObject(s, type));
                }
            }
            return newList;
        }

        /**
         * 根据参数 c 的值，移除列表中与参数 value 相等的元素。
         * @param key 要操作的key
         * @param c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
         * @param value 要匹配的值
         * @return 删除后的List中的记录数
         */
        public static long del(String key, int c, String value) {
            return redisTemplate.opsForList().remove(key, c, value);
        }

        /**
         * 根据参数 c 的值，移除列表中与参数 value 相等的元素。
         * @param key 要操作的key
         * @param c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
         * @param value 要匹配的值
         * @return 删除后的List中的记录数
         */
        public static <T> long del(String key, int c, T value) {
            return redisTemplate.opsForList().remove(key, c, JSON.toJSONString(value));
        }

        /**
         * 删除LIST的多个元素，只保留start与end之间的记录
         * @param key 要操作的key
         * @param start 记录的开始位置(0表示第一条记录)
         * @param end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
         * @return 成功返回true, 否则返回false
         */
        public static void trim(String key, int start, int end) {
            redisTemplate.opsForList().trim(key, start, end);
        }
    }

    // ////////////////////////////////////////////////////////
    // Hash
    // 所有关于哈希表的操作
    // ////////////////////////////////////////////////////////
    public static class Hash {
        /**
         * 从hash中删除指定的存储
         * @param key 要操作的Key
         * @param fieid 要删除的HashKey
         * @return 成功返回true，否则返回false
         */
        public static boolean del(String key, String fieid) {
            long s = redisTemplate.opsForHash().delete(key , fieid);
            return s == 1;
        }

        /**
         * 从hash中批量删除指定的存储
         * @param key    要操作的Key
         * @param fieids 要删除的HashKey的数组
         * @return 成功返回true，否则返回false
         */
        public static boolean del(String key, String[] fieids) {
            long s = 0;
            for (String fieid : fieids) {
                long f = redisTemplate.opsForHash().delete(key , fieid);
                s += f;
            }
            return s == fieids.length;
        }

        /**
         * 测试hash中指定的存储是否存在
         * @param key 要操作的Key
         * @param fieid 存储的名字
         * @return 存在返回true，否则返回false
         */
        public static boolean hasKey(String key, String fieid) {
            return redisTemplate.opsForHash().hasKey(key , fieid);
        }

        /**
         * 返回hash中指定存储位置的值
         * @param key 要操作的Key
         * @param fieid 存储的HashKey
         * @return 存储对应的值
         */
        public static String get(String key, String fieid) {
            Object s = redisTemplate.opsForHash().get(key, fieid);
            if(s != null){
               return s.toString();
            }else {
                return "";
            }
        }

        /**
         * 返回hash中指定存储位置的值
         * @param clazz 值的类型
         * @param key 要操作的Key
         * @param fieid 存储的HashKey
         * @return 存储对应的值
         */
        public static <T> List<T> get(Class<T> clazz, String key, String fieid) {
            String s = get(key, fieid);
            if (s != null && !s.isEmpty()){
                Type type = new TypeToken<List<T>>(){}.getType();
                return JSON.parseObject(s, type);
            }
            else{
                return null;
            }
        }

        /**
         * 以Map的形式返回hash中的存储和值
         * @param key 要操作的Key
         * @return 所有的键值对
         */
        public static Map<Object,Object> getAll(String key) {
            Map<Object,Object> map = redisTemplate.opsForHash().entries(key);
            return map;
        }

        /**
         * 以Map的形式返回hash中的存储和值
         * @param clazz 值的类型
         * @param key 要操作的Key
         * @return 所有的键值对
         */
        public static <T> Map<String, T> getAll(Class<T> clazz, String key) {
            Map<Object,Object> map = getAll(key);
            Map<String, T> newMap = new HashMap<String, T>();
            Iterator<Map.Entry<Object,Object>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String field = (String) entry.getKey();
                String val = (String) entry.getValue();
                Type type = new TypeToken<Map<String , T>>(){}.getType();
                newMap.put(field, (T) JSON.parseObject(val, type));
            }
            return newMap;
        }

        /**
         * 在Hash中添加一个键值对</br> 如果指定的Key不存在，则创建一个新的Key
         * @param key  要操作的Key
         * @param fieid 键
         * @param value 值
         * @return 成功返回true，否则返回false。当fieid已存时，value也被更新，但函数返回false
         */
        public static void put(String key, String fieid, String value) {
            redisTemplate.opsForHash().put(key, fieid, value);
        }

        /**
         * 在Hash中添加一个键值对</br> 如果指定的Key不存在，则创建一个新的Key
         * @param key 要操作的Key
         * @param fieid 键
         * @param value 值
         * @return 成功返回true，否则返回false。当fieid已存时，value也被更新，但函数返回false
         */
        public static <T> void put(String key, String fieid, T value) {
            put(key, fieid, JSON.toJSONString(value));
        }

        /**
         * 在Hash中添加一个键值对</br> 只有在fieid不存在时才执行，否则返回false
         * @param key 要操作的Key
         * @param fieid 键
         * @param value 值
         * @return 成功返回true，否则返回false。当fieid已存时，value不会被更新，函数返回false
         */
        public static boolean putIfAbsent(String key, String fieid, String value) {
            return redisTemplate.opsForHash().putIfAbsent(key, fieid, value);
        }

        /**
         * 在Hash中添加一个键值对</br> 只有在fieid不存在时才执行，否则返回false
         * @param key 要操作的Key
         * @param fieid 键
         * @param value 值
         * @return 成功返回true，否则返回false。当fieid已存时，value不会被更新，函数返回false
         */
        public static <T> boolean putIfAbsent(String key, String fieid, T value) {
            return putIfAbsent(key, fieid, JSON.toJSONString(value));
        }

        /**
         * 获取hash中value的集合
         * @param key 要操作的Key
         * @return 指定key的所有值列表（不包含键）
         */
        public static List<Object> values(String key) {
            return redisTemplate.opsForHash().values(key);
        }

        /**
         * 获取hash中value的集合
         * @param clazz 值的类型
         * @param key 要操作的Key
         * @return 指定key的所有值列表（不包含键）
         */
        public static <T> List<T> values(Class<T> clazz, String key) {
            List<Object> list = values(key);
            List<T> newList = new ArrayList<T>();
            Type type = new TypeToken<List<T>>(){}.getType();
            for (Object s : list) {
                if (s != null) {
                    newList.add((T) JSON.parseObject(s.toString(), type));
                }
            }
            return newList;
        }

        /**
         * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
         * @param key 要操作的Key
         * @param fieid 存储hashkey
         * @param value 要增加的值,可以是负数
         * @return 增加指定数字后，存储位置的值
         */
        public static long increment(String key, String fieid, long value) {
            return redisTemplate.opsForHash().increment(key, fieid, value);
        }

        /**
         * 返回指定hash中的所有存储hashkey
         * @param key 要操作的Key
         * @return hashkey的集合
         */
        public static Set<Object> keys(String key) {
            return redisTemplate.opsForHash().keys(key);
        }

        /**
         * 获取hash中存储的个数
         * @param key 要操作的Key
         * @return 存储的个数
         */
        public static long size(String key) {
            return redisTemplate.opsForHash().size(key);
        }

        /**
         * 根据多个fieids，获取对应的value，返回List,如果指定的fieids不存在,List对应位置为null
         * @param key 要操作的Key
         * @param fieids 存储位置
         * @return 指定fieids的所有值列表
         */
        public static List<Object> multiGet(String key, String fieids) {
            return redisTemplate.opsForHash().multiGet(key, Arrays.asList(fieids));
        }

        /**
         * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
         * @param key 要操作的Key
         * @param fieids 存储位置
         * @return 指定fieids的所有值列表
         */
        public static List<Object> multiGet(String key, List<String> fieids) {
            return redisTemplate.opsForHash().multiGet(key , Arrays.asList(fieids));
        }

        /**
         * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
         * @param clazz 值的类型
         * @param key 要操作的Key
         * @param fieids 存储位置
         * @return 指定fieids的所有值列表
         */
        public static <T> List<T> multiGet(Class<T> clazz, String key, String fieids) {
            List<Object> list = multiGet(key, fieids);
            List<T> newList = new ArrayList<T>();
            Type type = new TypeToken<List<T>>(){}.getType();
            for (Object s : list) {
                if (s == null)
                    newList.add(null);
                else
                    newList.add((T) JSON.parseObject(s.toString(), type));
            }
            return newList;
        }

        /**
         * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
         * @param clazz 值的类型
         * @param key 要操作的Key
         * @param fields 存储位置
         * @return 指定fieids的所有值列表
         */
        public static <T> List<T> multiGet(Class<T> clazz, String key, List<String> fields) {
            List<Object> list = multiGet(key, fields);
            List<T> newList = new ArrayList<T>();
            Type type = new TypeToken<List<T>>(){}.getType();
            for (Object s : list) {
                if (s == null)
                    newList.add(null);
                else
                    newList.add((T) JSON.parseObject(s.toString(), type));
            }
            return newList;
        }

        /**
         * 添加键值对，如果已存在，则覆盖
         * @param key  要操作的Key
         * @param map 键值对集合
         * @return 成功返回true，否则返回false
         */
        public static void putAll(String key, Map<String, String> map) {
            redisTemplate.opsForHash().putAll(key, map);
        }

        /**
         * 添加键值对，如果已存在，则覆盖
         * @param clazz  值的类型
         * @param key 要操作的Key
         * @param map 键值对集合
         * @return 成功返回true，否则返回false
         */
        public static <T> void putAll(Class<T> clazz, String key, Map<String, T> map) {
            Map<String, String> newMap = new HashMap<String, String>();
            Iterator<Map.Entry<String, T>> iter = map.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String field = (String) entry.getKey();
                T val = (T) entry.getValue();

                newMap.put(field, JSON.toJSONString(val));
            }
            putAll(key, newMap);
        }
    }

    // ////////////////////////////////////////////////////////
    // Sets
    // 所有关于集合的操作
    // ////////////////////////////////////////////////////////
    public static class Sets {
        /**
         * 向集合添加一条记录
         * @param key 要操作的Key
         * @param member 集合成员
         * @return 成功返回true，否则返回false（要添加的member已经存在）
         */
        public static boolean add(String key, String member) {
            long reply = redisTemplate.opsForSet().add(key, member);
            return reply == 1;
        }

        /**
         * 向集合添加一条记录
         * @param key 要操作的Key
         * @param member 集合成员
         * @return 成功返回true，否则返回false（要添加的member已经存在）
         */
        public static <T> boolean add(String key, T member) {
            return add(key, JSON.toJSONString(member));
        }

        /**
         * 获取指定key中元素个数
         * @param key 要查询的Key
         * @return 元素个数
         */
        public static long size(String key) {
            return redisTemplate.opsForSet().size(key);
        }

        /**
         * 通过给定的key求2个set变量的差值
         * @param key1  要比较的Key
         * @param key2  要比较的Key
         * @return 差异的成员集合
         */
        public static Set<String> difference(String key1 , String key2) {
            Set<String> set = redisTemplate.opsForSet().difference(key1 , key2);
            return set;
        }

        /**
         * 通过给定的key求2个set变量的差值
         * @param key1  要比较的Key
         * @param key2  要比较的Key
         * @param clazz 集合中数据的对象类型
         * @return 差异的成员集合
         */
        public static <T> Set<T> difference(Class<T> clazz, String key1 , String key2) {
            Set<String> set = difference(key1 , key2);
            Set<T> newSet = new HashSet<T>();
            Type type = new TypeToken<Set<T>>(){}.getType();
            for (String s : set)
                newSet.add((T) JSON.parseObject(s, type));

            return newSet;
        }

        /**
         * 获取变量中与给定集合中变量不一样的值
         * @param key 要比较的Key
         * @param list 要比较的集合
         * @return 差异的成员集合
         */
        public static Set<String> difference(String key , List<String> list) {
            Set<String> set = redisTemplate.opsForSet().difference(key , list);
            return set;
        }

        /**
         * 获取变量中与给定集合中变量不一样的值
         * @param clazz 集合中成员的类型
         * @param key 要比较的Key
         * @param list 要比较的集合
         * @return 差异的成员集合
         */
        public static <T> Set<T> diff(Class<T> clazz, String key , List<String> list) {
            Set<String> set = difference(key , list);
            Set<T> newSet = new HashSet<T>();
            Type type = new TypeToken<Set<T>>(){}.getType();
            for (String s : set)
                newSet.add((T) JSON.parseObject(s, type));

            return newSet;
        }

        /**
         * 获取2个变量中的交集
         * @param key1 要比较的Key1
         * @param key2 要比较的Key2
         * @return 交集成员的集合, 如果其中一个集合为不存在或为空，则返回空Set
         */
        public static Set<String> intersect(String key1 , String key2) {
            Set<String> set = redisTemplate.opsForSet().intersect(key1 , key2);
            return set;
        }

        /**
         * 返回给定集合交集的成员
         * @param clazz 集合中成员的类型
         * @param key1 要比较的Key1
         * @param key2 要比较的Key2
         * @return 交集成员的集合, 如果其中一个集合为不存在或为空，则返回空Set
         */
        public static <T> Set<T> inter(Class<T> clazz, String key1 , String key2) {
            Set<String> set = intersect(key1 , key2);
            Set<T> newSet = new HashSet<T>();
            Type type = new TypeToken<Set<T>>(){}.getType();
            for (String s : set) {
                if (s != null && !s.isEmpty())
                    newSet.add((T) JSON.parseObject(s, type));
            }
            return newSet;
        }

        /**
         *  获取多个变量之间的交集。
         * @param key
         * @param list
         * @return 交集成员的集合, 如果其中一个集合为不存在或为空，则返回空Set
         */
        public static Set<String> intersect(String key ,List<String> list) {
            return redisTemplate.opsForSet().intersect(key , list);
        }

        /**
         * 返回给定集合交集的成员
         * @param clazz 集合中成员的类型
         * @param key
         * @param list
         * @return 交集成员的集合, 如果其中一个集合为不存在或为空，则返回空Set
         */
        public static <T> Set<T> inter(Class<T> clazz, String key ,List<String> list) {
            Set<String> set = intersect(key , list);
            Set<T> newSet = new HashSet<T>();
            Type type = new TypeToken<Set<T>>(){}.getType();
            for (String s : set) {
                if (s != null && !s.isEmpty())
                    newSet.add((T) JSON.parseObject(s, type));
            }
            return newSet;
        }

        /**
         * 确定一个给定的值是否存在
         * @param key 要查找的Key
         * @param member 要判断的值
         * @return 存在返回true, 否则返回false
         */
        public static boolean isMember(String key, String member) {
            return redisTemplate.opsForSet().isMember(key, member);
        }

        /**
         * 确定一个给定的值是否存在
         * @param key 要查找的Key
         * @param member 要判断的值
         * @return 存在返回true, 否则返回false
         */
        public static <T> boolean isMember(String key, T member) {
            return isMember(key, JSON.toJSONString(member));
        }

        /**
         * 返回集合中的所有成员
         * @param key 要查找的Key
         * @return 成员集合
         */
        public static Set<String> members(String key) {
            return redisTemplate.opsForSet().members(key);
        }

        /**
         * 返回集合中的所有成员
         * @param key 要查找的Key
         * @param clazz 集合中成员的类型
         * @return 成员集合
         */
        public static <T> Set<T> members(Class<T> clazz, String key) {
            Set<String> set = members(key);
            Set<T> newSet = new HashSet<T>();
            for (String s : set) {
                if (s != null && !s.isEmpty())
                    newSet.add(JSON.parseObject(s, clazz));
            }
            return newSet;
        }

        /**
         * 转移变量的元素值到目的变量
         * 如果源集合不存在或不包哈指定成员，不进行任何操作，返回false<br/>
         * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
         * @param srckey 源集合
         * @param dstkey 目标集合
         * @param member 源集合中的成员
         * @return 转移成功返回true，否则返回false
         */
        public static boolean move(String srckey, String dstkey, String member) {
            return redisTemplate.opsForSet().move(srckey, dstkey, member);
        }

        /**
         * 将成员从源集合移出放入另一个集合 <br/>
         * 如果源集合不存在或不包哈指定成员，不进行任何操作，返回false<br/>
         * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
         * @param srckey 源集合
         * @param dstkey 目标集合
         * @param member 源集合中的成员
         * @return 转移成功返回true，否则返回false
         */
        public static <T> boolean move(String srckey, String dstkey, T member) {
            return move(srckey, dstkey, JSON.toJSONString(member));
        }

        /**
         * 从集合中删除指定成员
         * @param key  要操作的Key
         * @param member 要删除的成员
         * @return 成功返回true，失败返回false(成员不存在)
         */
        public static boolean del(String key, String member) {
            long s = redisTemplate.opsForSet().remove(key, member);
            return s == 1;
        }

        /**
         * 从集合中删除指定成员
         * @param key 要操作的Key
         * @param member 要删除的成员
         * @return 成功返回true，失败返回false(成员不存在)
         */
        public static <T> boolean del(String key, T member) {
            return del(key, JSON.toJSONString(member));
        }

        /**
         * 合并2个集合并返回合并后的结果<br/>
         * @param key1 要合并的集合
         * @param key2 要合并的集合
         * @return 合并后的结果集合
         */
        public static Set<String> union(String key1 , String key2) {
            return redisTemplate.opsForSet().union(key1 , key2);
        }

        /**
         * 合并多个集合并返回合并后的结果
         * @param keys  要合并的集合
         * @param clazz 集合中成员的类型
         * @return 合并后的结果集合
         */
        public static <T> Set<T> union(Class<T> clazz, String key ,String... keys) {
            Set<String> set = redisTemplate.opsForSet().union(key , Arrays.asList(keys));
            Set<T> newSet = new HashSet<T>();
            Type type = new TypeToken<Set<T>>(){}.getType();
            for (String s : set) {
                if (s != null && !s.isEmpty())
                    newSet.add((T) JSON.parseObject(s, type));
            }
            return newSet;
        }

        /**
         * 合并多个集合并返回合并后的结果
         * @param key 要合并的集合key
         * @param list 要合并的集合
         * @return 合并后的结果集合
         */
        public static Set<String> union(String key ,List<String> list) {
            return redisTemplate.opsForSet().union(key , list);
        }

        /**
         * 合并多个集合并返回合并后的结果<br/>
         * 合并后的结果不会保存在redis中
         * @param clazz 集合中数据的类型
         * @param key 要合并的集合key
         * @param list 要合并的集合
         * @return 合并后的结果集合
         */

        public static <T> Set<T> union(Class<T> clazz, String key ,List<String> list) {
            Set<String> set = union(key , list);
            Set<T> newSet = new HashSet<T>();
            Type type = new TypeToken<Set<T>>(){}.getType();
            for (String s : set) {
                if (s != null && !s.isEmpty())
                    newSet.add((T) JSON.parseObject(s, type));
            }
            return newSet;
        }
    }

    // ////////////////////////////////////////////////////////
    // SortSet
    // 所有关于可排序集合的操作
    // ////////////////////////////////////////////////////////
    public static class SortSet {
        /**
         * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
         * @param key 要添加记录的集合Key
         * @param score 权重
         * @param member 要加入的值，
         * @return 成功返回true，否则返回false(member已存在)
         */
        public static boolean add(String key, double score, String member) {
            return redisTemplate.opsForZSet().add(key , member , score);
        }

        /**
         * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
         * @param key 要添加记录的集合Key
         * @param score 权重
         * @param member 要加入的值，
         * @return 成功返回true，否则返回false(member已存在)
         */
        public static <T> boolean add(String key, double score, T member) {
            return add(key, score, JSON.toJSONString(member));
        }

        /**
         * 获取集合中元素的数量
         * @param key 要查询的Key
         * @return 元素数量，如果返回0则集合不存在
         */
        public static long size(String key) {
            return redisTemplate.opsForZSet().size(key);
        }

        /**
         * 获取集合内SCORE在一个给定范围内的成员总数
         * @param key 要查询的集合Key
         * @param min 最小排序位置
         * @param max 最大排序位置
         * @return 元素数量，如果返回0则集合不存在
         */
        public static long count(String key, double min, double max) {
            return redisTemplate.opsForZSet().count(key, min, max);
        }

        /**
         * 为集合中的成员自增SCORE
         * @param key 要查询的集合Key
         * @param score 要增的权重
         * @param member 要操作的成员
         * @return 增后的权重
         */
        public static double incrementScore(String key, double score, String member) {
            return redisTemplate.opsForZSet().incrementScore(key , member , score);
        }

        /**
         * 为集合中的成员自增SCORE
         * @param key 要查询的集合Key
         * @param score 要增的权重
         * @param member 要操作的成员
         * @return 增后的权重
         */
        public static <T> double incrementScore(String key, double score, T member) {
            return incrementScore(key, score, JSON.toJSONString(member));
        }

        /**
         * 返回集合中一定INDEX范围内的成员 , 为第一个元素，-1为最后一个元素 , 集合必须按照score 值递增
         * @param key 要操作的集合key
         * @param start 开始位置(包含)
         * @param end 结束位置(包含)
         * @return 指定了范围的子集合
         */
        public static Set<String> range(String key, int start, int end) {
            return redisTemplate.opsForZSet().range(key, start, end);
        }

        /**
         * 返回集合中一定INDEX范围内的成员,0为第一个元素，-1为最后一个元素,集合必须按照score 值递增
         * @param clazz 集合中数据的类型
         * @param key 要操作的集合key
         * @param start 开始位置(包含)
         * @param end 结束位置(包含)
         * @return 指定了范围的子集合
         */
        public static <T> Set<T> range(Class<T> clazz, String key, int start, int end) {
            Set<String> set = range(key, start, end);
            Set<T> newSet = new HashSet<T>();
            for (String s : set) {
                if (s != null && !s.isEmpty())
                    newSet.add(JSON.parseObject(s, clazz));
            }
            return newSet;
        }

        /**
         * 返回集合中一定SCORE范围内的成员<br/>
         * 集合必须按照score 值递增
         * @param key 要操作的集合key
         * @param min 上限权重
         * @param max 下限权重
         * @return 指定了范围的子集合
         */
        public static Set<String> rangeByScore(String key, double min, double max) {
            if (max < 0)
                max = Double.MAX_VALUE;

            return redisTemplate.opsForZSet().rangeByScore(key, min, max);
        }

        /**
         * 返回集合中一定SCORE范围内的成员,集合必须按照score 值递增
         * @param clazz 集合中数据的类型
         * @param key 要操作的集合key
         * @param min 上限权重
         * @param max 下限权重
         * @return 指定了范围的子集合
         */
        public static <T> Set<T> rangeByScore(Class<T> clazz, String key, double min, double max) {
            if (max < 0)
                max = Double.MAX_VALUE;

            Set<String> set = rangeByScore(key, min, max);
            Set<T> newSet = new HashSet<T>();
            Type type = new TypeToken<Set<T>>(){}.getType();
            for (String s : set) {
                if (s != null && !s.isEmpty())
                    newSet.add((T) JSON.parseObject(s,  type));
            }
            return newSet;
        }

        /**
         * 获取指定值在集合中的位置，集合的排序必须从低到高
         * @param key 要操作的集合key
         * @param member 成员值
         * @return 指定值的位置
         */
        public static long rank(String key, String member) {
            return redisTemplate.opsForZSet().rank(key, member);
        }

        /**
         * 获取指定值在集合中的位置，集合的排序必须从低到高
         * @param key 要操作的集合key
         * @param member 成员值
         * @return 指定值的位置
         */
        public static <T> long rank(String key, T member) {
            return rank(key, JSON.toJSONString(member));
        }

        /**
         * 从集合中删除成员
         * @param key 要操作的集合key
         * @param member 成员值
         * @return 成功返回true， 否则返回false
         */
        public static boolean del(String key, String member) {
            long s = redisTemplate.opsForZSet().remove(key , member);
            return s == 1;
        }

        /**
         * 从集合中删除成员
         * @param key 要操作的集合key
         * @param member 成员值
         * @return 成功返回true， 否则返回false
         */
        public static <T> boolean del(String key, T member) {
            return del(key, JSON.toJSONString(member));
        }

        /**
         * 删除给定位置区间的元素
         * @param key 要操作的集合key
         * @param start 开始区间，从0开始(包含)
         * @param end 结束区间,-1为最后一个元素(包含)
         * @return 删除的数量
         */
        public static long removeRange(String key, int start, int end) {
            return redisTemplate.opsForZSet().removeRange(key, start, end);
        }

        /**
         * 删除给定权重区间的元素
         * @param key 要操作的集合key
         * @param min 下限权重(包含)
         * @param max 上限权重(包含)
         * @return 删除的数量
         */
        public static long delByScore(String key, double min, double max) {
            return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        }

        /**
         * 获取给定值在集合中的权重
         * @param key 要操作的集合key
         * @param member 要查找的成员
         * @return 指定成员的权重
         */
        public static double score(String key, String member) {
            Double score = redisTemplate.opsForZSet().score(key, member);
            if (score != null)
                return score;
            return 0;
        }

        /**
         * 获取给定值在集合中的权重
         * @param key
         * @param member
         * @return double 权重
         */
        public static <T> double score(String key, T member) {
            Double score = score(key, JSON.toJSONString(member));
            if (score != null)
                return score;
            return 0;
        }
    }

    // ////////////////////////////////////////////////////////
    // Keys
    // 所有关于Key的操作，以及排序操作
    // ////////////////////////////////////////////////////////
    public static class Keys {
        /**
         * 清空所有key
         */
        public static void flushAll() {
            Set<String> keys = redisTemplate.keys("*");
            redisTemplate.delete(keys);
        }

        /**
         * 将 oldkey 改名为 newkey
         * 当 newkey 已经存在时， 将覆盖旧值 当 oldkey 和 newkey 相同，或者 oldkey 不存在时，返回false;
         * @param oldkey 要更名的key名称
         * @param newkey  新名称
         * @return 成功返回true，否则返回false
         */
        public static void rename(String oldkey, String newkey) {
            redisTemplate.rename(oldkey, newkey);
        }

        /**
         * 将 oldkey 改名为 newkey
         * 当 newkey 已经存在时，返回false
         * 当 oldkey 不存在时，返回false
         * @param oldkey 要更名的key名称
         * @param newkey 新名称
         * @return 更名是否成功
         */
        public static boolean renameIfAbsent(String oldkey, String newkey) {
            return redisTemplate.renameIfAbsent(oldkey, newkey);
        }

        /**
         * 设置key的过期时间，以秒为单位
         * @param key 要修改的Key
         * @param seconds 过期时间，以秒为单位,<=0表示默认时间
         * @return 设置是否成功
         */
        public static boolean expired(String key, int seconds) {
            if (seconds <= 0)
                seconds = expire;

            return redisTemplate.expire(key, seconds , TimeUnit.SECONDS);
        }

        /**
         * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
         * @param key 要修改的Key
         * @param timestamp 过期时间
         * @return 设置是否成功
         */
        public static boolean expireAt(String key, long timestamp) {
            return redisTemplate.expireAt(key,  new Date(timestamp));
        }

        /**
         * 查询key的过期时间
         * @param key
         * @return 以秒为单位的时间表示
         */
        public static long getExpireTime(String key) {
            return redisTemplate.getExpire(key);
        }

        /**
         * 取消对key过期时间的设置
         * @param key 要设置的key
         * @return 操作是否成功
         */
        public static boolean persist(String key) {
            return redisTemplate.persist(key);
        }

        /**
         * 删除keys对应的记录,可以是多个key
         * @param key 要删除的Key
         * @return 删除的记录数
         */
        public  static boolean del(String key) {
            if (key.length() == 0)
                return false;

            return redisTemplate.delete(key);
        }

        /**
         * 删除keys对应的记录,可以是多个key
         * @param keys 要删除的Key
         * @return 删除的记录数
         */
        public static long del(List<String> keys) {
            return redisTemplate.delete(keys);
        }

        /**
         * 判断key是否存在
         * @param key 要查找的Key
         * @return 存在返回true，否则返回false
         */
        public static boolean hasKey(String key) {
            return redisTemplate.hasKey(key);
        }

        /**
         * 对List,Set,SortSet进行排序, 如果集合数据较大应避免使用这个方法
         * @param key 要排序的集合key
         * @return 排序后集合的全部记录
         */
        public static List<String> sort(String key) {
            SortQuery<String> query = SortQueryBuilder.sort(key).build();
            return redisTemplate.sort(query);
        }

        /**
         * 对List,Set,SortSet进行排序, 如果集合数据较大应避免使用这个方法
         * 如果集合中的类型为对象，则按照转换为JSON后的字符串排序
         * @param clazz 集合中数据的对象类型
         * @param key  要排序的集合key
         * @return 排序后集合的全部记录
         */
        public static <T> List<T> sort(Class<T> clazz, String key) {
            List<String> list = sort(key);
            List<T> newList = new ArrayList<T>();
            Type type = new TypeToken<List<T>>(){}.getType();
            for (String s : list) {
                if (s != null && !s.isEmpty())
                    newList.add((T) JSON.parseObject(s, type));
            }

            return newList;
        }

        /**
         * 对List,Set,SortSet进行排序或limit
         * @param key 要排序的key
         * @param query 排序类型或limit的起止位置.
         * @return 排序后的全部或部分记录(limit决定)

        public List<String> sort(String key, SortQuery<String> query) {
            SortQueryBuilder.sort(key).
            List<String> list = sjedis.sort(key, parame);
            returnJedis(sjedis);
            return list;
        }*/

        /**
         * 对List,Set,SortSet进行排序或limit
         * @param key 要排序的key
         * @param parame 排序类型或limit的起止位置.
         * @param clazz 集合中数据的对象类型
         * @return 排序后的全部或部分记录(limit决定)

        public <T> List<T> sort(Class<T> clazz, String key, SortingParams parame) {
            Jedis sjedis = getJedis();
            List<String> list = sjedis.sort(key, parame);
            returnJedis(sjedis);

            List<T> newList = new ArrayList<T>();
            for (String s : list) {
                if (s != null && !s.isEmpty())
                    newList.add((T) JSON.parseObject(s, (Type) clazz));
            }

            return newList;
        }*/

        /**
         * 返回指定key存储的类型
         * @param key 要查询的key
         * @return 如下类型之一：none|string|list|set|zset|hash
         */
        public static String type(String key) {
            return redisTemplate.type(key).code();
        }

        /**
         * 查找所有匹配给定的模式的键
         * @param pattern key的表达式 *表示多个，？表示一个
         * @return       ,
         */
        public static Set<String> keys(String pattern) {
            Set<String> set = redisTemplate.keys(pattern);
            return set;
        }
    }
}
