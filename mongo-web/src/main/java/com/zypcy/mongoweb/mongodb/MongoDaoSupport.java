package com.zypcy.mongoweb.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zypcy.mongoweb.config.SpringContextApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.zypcy.mongoweb.mongodb.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
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
@Service
public class MongoDaoSupport implements MongoDao {

    protected MongoTemplate mongoTemplate = SpringContextApplication.getBean(MongoTemplate.class);

    @Override
    public <T> T save(Class<T> clazz) {
        checkMongoConnection();
        mongoTemplate.save(clazz);
        return null;
    }

    @Override
    public T save(T bean) {
        checkMongoConnection();
        mongoTemplate.save(bean);
        return bean;
    }

    @Override
    public T save(T bean, String collectionName) {
        checkMongoConnection();
        mongoTemplate.save(bean, collectionName);
        return bean;
    }

    @Override
    public DeleteResult deleteById(T t) {
        checkMongoConnection();
        return mongoTemplate.remove(t);
    }

    @Override
    public DeleteResult deleteById(String id, String collectionName) {
        checkMongoConnection();
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, collectionName);
    }

    @Override
    public DeleteResult deleteByCondition(T t) {
        checkMongoConnection();
        Query query = buildBaseQuery(t);
        return mongoTemplate.remove(query, this.getEntityClass());
    }

    @Override
    public UpdateResult update(Query query, Update update) {
        checkMongoConnection();
        return mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    @Override
    public UpdateResult updateById(String id, T t) {
        checkMongoConnection();
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = buildBaseUpdate(t);
        return update(query, update);
    }

    @Override
    public List<T> list(T t) {
        checkMongoConnection();
        Query query = buildBaseQuery(t);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> list(Query query) {
        checkMongoConnection();
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> list(Query query, String collectionName) {
        checkMongoConnection();
        return mongoTemplate.find(query, this.getEntityClass(), collectionName);
    }

    @Override
    public List<T> listPage(T t, Page page) {
        checkMongoConnection();
        int skip = (int) (page.getPageSize() * (page.getPageIndex() - 1));
        int limit = Math.toIntExact(page.getPageSize());
        Query query = buildBaseQuery(t);
        query.skip(skip).limit(limit);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> listPage(T t, Page page, String collectionName) {
        checkMongoConnection();
        int skip = (int) (page.getPageSize() * (page.getPageIndex() - 1));
        int limit = Math.toIntExact(page.getPageSize());
        Query query = buildBaseQuery(t);
        query.skip(skip).limit(limit);
        return mongoTemplate.find(query, this.getEntityClass(), collectionName);
    }

    @Override
    public long getCount(T t, Page page) {
        checkMongoConnection();
        Query query = buildBaseQuery(t);
        return mongoTemplate.count(query, this.getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        checkMongoConnection();
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    @Override
    public T findOne(Query query, String collectionName) {
        checkMongoConnection();
        return mongoTemplate.findOne(query, this.getEntityClass(), collectionName);
    }

    @Override
    public T getById(String id) {
        checkMongoConnection();
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    @Override
    public T getById(String id, String collectionName) {
        checkMongoConnection();
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    @Override
    public MongoTemplate getMongoTemplate() {
        checkMongoConnection();
        return mongoTemplate;
    }

    /**
     * 查询MongoDB连接
     */
    private void checkMongoConnection() {
        if (mongoTemplate == null) {
            throw new RuntimeException("mongodb connection is down...");
        }
    }

    /**
     * 获取需要操作的实体类class
     *
     * @return
     */
    protected Class<T> getEntityClass() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    /**
     * 根据entity构建查询条件Query
     *
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
