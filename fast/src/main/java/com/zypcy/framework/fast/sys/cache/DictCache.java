package com.zypcy.framework.fast.sys.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.zypcy.framework.fast.common.util.RedisUtil;
import com.zypcy.framework.fast.sys.constant.InitLoaderConstant;
import com.zypcy.framework.fast.sys.constant.KeyConstant;
import com.zypcy.framework.fast.sys.entity.ZySysDict;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据字典缓存
 * local：本地 list 存储字典信息
 * redis：必须配置redis远程地址与帐号
 */
public class DictCache {

    private static Type type = new TypeToken<List<Object>>(){}.getType();

    /**
     * 本地内存缓存
     */
    private static List<ZySysDict> dicts = new ArrayList<>();

    /**
     * 获取本地内存
     * @return
     */
    public static List<ZySysDict> getLocalDicts(){
        return dicts;
    }

    /**
     * 存储dict
     * @param dict
     */
    public static void addDict(ZySysDict dict){
        if(InitLoaderConstant.SessionStickType.equals("local")) {
            dicts.add(dict);
        }else {
            //在redis中存储2份数据结构，1.Hash中key是parentId，value是所有下级id，2.Hash中存储所有字典
            List<Object> ids = redisGetIdsByPId(dict.getParentId());
            ids.add(dict.getId());
            RedisUtil.Hash.put(KeyConstant.Dict_Parent_Info , dict.getParentId() , ids);
            RedisUtil.Hash.put(KeyConstant.Dict_Info , dict.getId() , dict);
        }
    }

    /**
     * Redis中通过PId获取它的所有子集 字典Id
     * @param pId
     * @return
     */
    private static List<Object> redisGetIdsByPId(String pId){
        List<Object> ids =  new ArrayList<>();
        String str = RedisUtil.Hash.get(KeyConstant.Dict_Parent_Info , pId);
        if(!StringUtils.isEmpty(str)){
            ids = JSON.parseObject(str , type);
        }
        return ids;
    }

    /**
     * 更新dict
     * @param dict
     */
    public static void updateDict(ZySysDict dict){
        if(InitLoaderConstant.SessionStickType.equals("local")) {
            dicts.removeIf( d -> dict.getId().equals(d.getId()));
            dicts.add(dict);
        }else {
            RedisUtil.Hash.put(KeyConstant.Dict_Info , dict.getId() , dict);
        }
    }

    /**
     * 根据Id获取数据字典
     * @param id
     * @return
     */
    public static ZySysDict getDictById(String id){
        if(InitLoaderConstant.SessionStickType.equals("local")) {
            Optional<ZySysDict> cacheDict = dicts.stream().filter(d -> id.equals(d.getId())).findAny();
            return cacheDict.get();
        }else {
            return RedisUtil.Hash.get(ZySysDict.class , KeyConstant.Dict_Info , id);
        }
    }

    /**
     * 根据Id删除数据字典
     * @param id
     */
    public static void delDictById(String id){
        if(InitLoaderConstant.SessionStickType.equals("local")) {
            //通过type删除自己与所有下级
            Optional<ZySysDict> dict = dicts.stream().filter(d -> id.equals(d.getId())).findAny();
            if(dict.get() != null){
                String type = dict.get().getType();
                dicts.removeIf( d -> type.equals(d.getType()));
            }
        }else {
            //找打它所在的上级与下级，都要对应删除
            ZySysDict dict = RedisUtil.Hash.get(ZySysDict.class , KeyConstant.Dict_Info , id);
            if(dict != null){
                //先删与父级之间的关系
                String pId = dict.getParentId();
                List<Object> ids = redisGetIdsByPId(pId);
                ids.removeIf( str -> id.equals(str));
                RedisUtil.Hash.put(KeyConstant.Dict_Parent_Info , pId , ids);

                //删除3级，子集、子集的下级、以及自己
                List<Object> ids2 = redisGetIdsByPId(id);
                if(ids2 != null && ids2.size() > 0){
                    ids2.forEach( o -> {
                        String ccid = o.toString();
                        List<Object> ids3 = redisGetIdsByPId(ccid);
                        //子集的下级
                        RedisUtil.Hash.del(KeyConstant.Dict_Info , ids3.toArray());
                    });
                    //子集
                    RedisUtil.Hash.del(KeyConstant.Dict_Info , ids2.toArray());
                }
                //删除自己
                RedisUtil.Hash.del(KeyConstant.Dict_Info , id);
                RedisUtil.Hash.del(KeyConstant.Dict_Parent_Info ,id);
            }
        }
    }

    /**
     * 根据父Id获取数据字典集合
     * @param pId
     * @return
     */
    public static List<ZySysDict> getDictsByPId(String pId){
        if(InitLoaderConstant.SessionStickType.equals("local")) {
            return dicts.stream().filter( dict -> pId.equals(dict.getParentId())).collect(Collectors.toList());
        }else {
            //从父子关系集合中取出该父Id下的所有子集字典id，再从所有数据字典中取出字典
            List<Object> ids = redisGetIdsByPId(pId);
            return RedisUtil.Hash.multiGet(ZySysDict.class , KeyConstant.Dict_Info , ids);
        }
    }
}
