package com.zypcy.mongoweb.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zypcy.mongoweb.mongodb.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @Author zhuyu
 * @Time 2020-06-09 09:38
 * @Description MongoDao
 */
public interface MongoDao<T> {

    /**
     * 保存一个对象到mongodb
     * @param bean 要保存的对象
     * @author zhuyu
     * @date 2020/6/9 14:45
     */
    T save(T bean);

    /**
     * 保存一个对象到mongodb
     * @param bean 要保存的对象
     * @param collectionName 表名
     * @author zhuyu
     * @date 2020/6/9 14:45
     */
    T save(T bean , String collectionName);

    /**
     * 根据id删除对象
     * @param t  删除对象
     * @author zhuyu
     * @date 2020/6/9 14:45
     */
    DeleteResult deleteById(T t);

    /**
     * 根据id删除对象
     * @param t  删除对象
     * @param collectionName 表名
     * @author zhuyu
     * @date 2020/6/9 14:45
     */
    DeleteResult deleteById(String id , String collectionName);

    /**
     * 根据对象的属性删除
     * @param t 对象的属性
     * @author zhuyu
     * @date 2020/6/9 14:45
     */
    DeleteResult deleteByCondition(T t);

    /**
     * 通过条件查询更新数据
     * @param query  条件
     * @param update 更新数据
     * @author zhuyu
     * @date 2020/6/9 14:48
     */
    UpdateResult update(Query query, Update update);

    /**
     * 根据id进行更新
     * @param id id
     * @param t  更新对象
     * @author zhuyu
     * @date 2020/6/9 14:49
     */
    UpdateResult updateById(String id, T t);

    /**
     * 通过条件查询实体(集合)
     * @param query 查询条件
     * @author zhuyu
     * @date 2020/6/9 14:49
     */
    List<T> list(T t);

    /**
     * 通过条件查询实体(集合)
     * @param query 查询条件
     * @author zhuyu
     * @date 2020/6/9 14:49
     */
    List<T> list(Query query);

    /**
     * 通过条件查询实体(集合)
     * @param query 查询条件
     * @param collectionName 表名
     * @author zhuyu
     * @date 2020/6/9 14:49
     */
    List<T> list(Query query , String collectionName);

    /**
     * 通过条件分页查询实体(集合)
     * @param t    查询条件
     * @param page 分页条件
     * @author zhuyu
     * @date 2020/6/9 14:50
     */
    List<T> listPage(T t, Page page);

    /**
     * 通过条件分页查询实体(集合)
     * @param t    查询条件
     * @param page 分页条件
     * @param collectionName 表名
     * @author zhuyu
     * @date 2020/6/9 14:50
     */
    List<T> listPage(T t, Page page , String collectionName);

    /**
     * 查询总记录数
     * @param t    查询条件
     * @param page 分页条件
     * @author zhuyu
     * @date 2020/6/9 14:51
     */
    long getCount(T t, Page page);

    /**
     * 通过一定的条件查询一个实体
     * @param query 查询条件
     * @author zhuyu
     * @date 2020/6/9 14:51
     */
    T findOne(Query query);

    /**
     * 通过一定的条件查询一个实体
     * @param query 查询条件
     * @param collectionName 表名
     * @author zhuyu
     * @date 2020/6/9 14:51
     */
    T findOne(Query query, String collectionName);

    /**
     * 通过ID获取记录
     * @param id id
     * @author zhuyu
     * @date 2020/6/9 14:51
     */
    T getById(String id);

    /**
     * 通过ID获取记录,并且指定了集合名(表的意思)
     * @param id             id
     * @param collectionName 表名
     * @author zhuyu
     * @date 2020/6/9 14:51
     */
    T getById(String id, String collectionName);

    /**
     * 获取MongoTemplate
     * @author zhuyu
     * @date 2020/6/9 14:51
     */
    MongoTemplate getMongoTemplate();
}
