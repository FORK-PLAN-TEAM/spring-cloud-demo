package com.zypcy.mongoweb.mongodb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zhuyu
 * @Time 2020-06-09 09:53
 * @Description Mongodb自定义注解 描述
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoQueryField {

    /**
     * type表示查询类型，默认为equals
     */
    MongoQueryType type() default MongoQueryType.EQUALS;

    /**
     * sort表示排序方式 asc升序 desc降序
     */
    String sort() default "";

}
