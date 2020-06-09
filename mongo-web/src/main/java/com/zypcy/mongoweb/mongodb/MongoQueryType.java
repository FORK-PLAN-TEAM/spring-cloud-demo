package com.zypcy.mongoweb.mongodb;

import org.springframework.data.mongodb.core.query.Criteria;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author zhuyu
 * @Time 2020-06-09 09:54
 * @Description Mongodb类型
 */
public enum MongoQueryType {

    EQUALS {
        @Override
        public Criteria buildCriteria(MongoQueryField queryFieldAnnotation, Field field, Object value) {
            if (check(queryFieldAnnotation, field, value)) {
                return Criteria.where(field.getName()).is(value);
            }
            return new Criteria();
        }
    },
    LIKE {
        @Override
        public Criteria buildCriteria(MongoQueryField queryFieldAnnotation, Field field, Object value) {
            if (check(queryFieldAnnotation, field, value)) {
                return Criteria.where(field.getName()).regex(value.toString());
            }
            return new Criteria();
        }
    },
    IN {
        @Override
        public Criteria buildCriteria(MongoQueryField queryFieldAnnotation, Field field, Object value) {
            if (check(queryFieldAnnotation, field, value)) {
                if (value instanceof List) {
                    // 此处必须转型为List，否则会在in外面多一层[]
                    return Criteria.where(field.getName()).in((List<?>) value);
                }
            }
            return new Criteria();
        }
    },
    GREATER_THAN {
        @Override
        public Criteria buildCriteria(MongoQueryField queryFieldAnnotation, Field field, Object value) {
            if (check(queryFieldAnnotation, field, value)) {
                return Criteria.where(field.getName()).gte(value);
            }
            return new Criteria();
        }
    },
    LESS_THAN {
        @Override
        public Criteria buildCriteria(MongoQueryField queryFieldAnnotation, Field field, Object value) {
            if (check(queryFieldAnnotation, field, value)) {
                return Criteria.where(field.getName()).lte(value);
            }
            return new Criteria();
        }
    };

    private static boolean check(MongoQueryField queryField, Field field, Object value) {
        return !(queryField == null || field == null || value == null);
    }

    public abstract Criteria buildCriteria(MongoQueryField queryFieldAnnotation, Field field, Object value);

}
