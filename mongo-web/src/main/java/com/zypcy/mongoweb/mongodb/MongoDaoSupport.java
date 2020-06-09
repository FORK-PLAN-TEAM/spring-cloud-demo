package com.zypcy.mongoweb.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.zypcy.mongoweb.mongodb.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author 64590 作者
 * @Time 2020-06-09 09:45
 * @Description MongoDao实现类 描述
 */
public abstract class MongoDaoSupport<T> implements MongoDao<T> {

    @Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;

    @Override
    public T save(T bean) {
        mongoTemplate.save(bean);
        return bean;
    }

    @Override
    public T save(T bean, String collectionName) {
        mongoTemplate.save(bean , collectionName);
        return bean;
    }

    @Override
    public DeleteResult deleteById(T t) {
        return mongoTemplate.remove(t);
    }

    @Override
    public DeleteResult deleteById(String id, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.remove(query , collectionName);
    }

    @Override
    public DeleteResult deleteByCondition(T t) {
        Query query = buildBaseQuery(t);
        return mongoTemplate.remove(query , this.getEntityClass());
    }

    @Override
    public UpdateResult update(Query query, Update update) {
        return mongoTemplate.updateMulti(query , update , this.getEntityClass());
    }

    @Override
    public UpdateResult updateById(String id, T t) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = buildBaseUpdate(t);
        return update(query, update);
    }

    @Override
    public List<T> list(T t) {
        Query query = buildBaseQuery(t);
        return mongoTemplate.find(query , this.getEntityClass());
    }

    @Override
    public List<T> list(Query query) {
        return mongoTemplate.find(query , this.getEntityClass());
    }

    @Override
    public List<T> list(Query query, String collectionName) {
        return mongoTemplate.find(query , this.getEntityClass() , collectionName);
    }

    @Override
    public List<T> listPage(T t, Page page) {
        int skip = (int) (page.getPageSize() * (page.getPageIndex() - 1));
        int limit = Math.toIntExact(page.getPageSize());
        Query query = buildBaseQuery(t);
        query.skip(skip).limit(limit);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> listPage(T t, Page page, String collectionName) {
        int skip = (int) (page.getPageSize() * (page.getPageIndex() - 1));
        int limit = Math.toIntExact(page.getPageSize());
        Query query = buildBaseQuery(t);
        query.skip(skip).limit(limit);
        return mongoTemplate.find(query, this.getEntityClass() , collectionName);
    }

    @Override
    public long getCount(T t, Page page) {
        Query query = buildBaseQuery(t);
        return mongoTemplate.count(query, this.getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query , this.getEntityClass());
    }

    @Override
    public T findOne(Query query, String collectionName) {
        return mongoTemplate.findOne(query , this.getEntityClass() , collectionName);
    }

    @Override
    public T getById(String id) {
        return mongoTemplate.findById(id , this.getEntityClass());
    }

    @Override
    public T getById(String id, String collectionName) {
        return mongoTemplate.findById(id , this.getEntityClass() , collectionName);
    }

    @Override
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    /**
     * 获取需要操作的实体类class
     * @return
     */
    protected Class<T> getEntityClass() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    /**
     * 根据entity构建查询条件Query
     * @param t
     * @return
     */
    private Query buildBaseQuery(T t) {
        Query query = new Query();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                MongoQueryField queryField = field.getAnnotation(MongoQueryField.class);
                if (Objects.nonNull(queryField)) {
                    if (Objects.nonNull(value) && !StringUtils.isEmpty(value.toString())) {
                        query.addCriteria(queryField.type().buildCriteria(queryField, field, value));
                    }
                    String sort = queryField.sort();
                    if (!StringUtils.isEmpty(sort)) {
                        query.with(Sort.by(Sort.Direction.valueOf(sort.toUpperCase()), field.getName()));
                    }
                }
            } catch (IllegalAccessException ignored) {
                throw new RuntimeException("构建查询条件失败，" + ignored.getMessage());
            }
        }
        return query;
    }

    /**
     * @param t
     * @return
     */
    private Update buildBaseUpdate(T t) {
        Update update = new Update();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (Objects.nonNull(value)) {
                    update.set(field.getName(), value);
                }
            } catch (IllegalAccessException ignored) {
                throw new RuntimeException("构建查询条件失败，" + ignored.getMessage());
            }
        }
        return update;
    }
}
