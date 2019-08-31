package com.zypcy.framework.fast.sys.async;

import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.common.util.RedisUtil;
import com.zypcy.framework.fast.sys.cache.DictCache;
import com.zypcy.framework.fast.sys.constant.InitLoaderConstant;
import com.zypcy.framework.fast.sys.constant.KeyConstant;
import com.zypcy.framework.fast.sys.service.IZySysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 异步方法
 1.方法名必须是public进行修饰的
 2.必须不能在同一个类中调用异步方法
 3.通过Spring 注解把该类注入到容器，再调用异步方法
 */
@Service
public class InitLoaderAsync {

    @Autowired
    IZySysDictService dictService;

    /**
     * 如果使用redis作为缓存，则启动时预热一下
     */
    @Async("taskExecutor")
    public void initRedisCache(){
        if(InitLoaderConstant.SessionStickType.equals("redis")){
            LogUtil.info("系统使用redis，共有：" + RedisUtil.Keys.countKeys() + " 个key");
        }
    }

    /**
     * 初始化数据字典
     */
    @Async("taskExecutor")
    public void initDictCache(){
        //TODO 如果数据过多，此方法载入到缓存会有问题
        if(InitLoaderConstant.SessionStickType.equals("redis")){
            //判断redis中是否有数据字典的key，没有则加入到缓存
            boolean flag = RedisUtil.Keys.hasKey(KeyConstant.Dict_Info);
            if(!flag){
                dictService.getAllDicts().forEach( dict -> DictCache.addDict(dict));
            }
        }else {
            DictCache.getLocalDicts().addAll(dictService.getAllDicts());
        }
    }

    /**
     * 异步任务，返回处理结果
     * @return
     */
    @Async("taskExecutor")
    public Future<List<String>> getList(){
        List<String> list = new ArrayList<>();
        try {
            LogUtil.info("开始处理任务1");
            for(int i=0; i < 100000;i++){
                list.add(i+"");
            }
            //让线程睡2秒
            Thread.sleep(1500);
            LogUtil.info("任务1处理完成");
        } catch (Exception e) {

        }
        return new AsyncResult<>(list);
    }

    /**
     * 异步任务，返回处理结果
     * @return
     */
    @Async("taskExecutor")
    public Future<List<String>> getList2(){
        List<String> list = new ArrayList<>();
        try {
            LogUtil.info("开始处理任务2");
            for(int i=0; i < 100000;i++){
                list.add(i+"");
            }
            //让线程睡2秒
            Thread.sleep(1500);
            LogUtil.info("任务2处理完成");
        } catch (Exception e) {

        }
        return new AsyncResult<>(list);
    }
}
