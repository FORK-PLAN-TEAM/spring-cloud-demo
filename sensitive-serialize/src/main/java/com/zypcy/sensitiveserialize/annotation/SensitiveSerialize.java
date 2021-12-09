package com.zypcy.sensitiveserialize.annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.zypcy.sensitiveserialize.util.SensitiveUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * 脱敏处理类
 *
 * @Author zhuyu
 * @Time 2020-06-19 10:53
 * @Description 脱敏处理类
 */
public class SensitiveSerialize extends JsonSerializer implements ContextualSerializer {

    private SensitiveType type;

    public SensitiveSerialize() {
    }

    public SensitiveSerialize(final SensitiveType type) {
        this.type = type;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty beanProperty) throws JsonMappingException {
        System.out.println("createContextual...");
        if (beanProperty != null) {
            Sensitive sensitive = beanProperty.getAnnotation(Sensitive.class);
            if (sensitive == null) {
                sensitive = beanProperty.getContextAnnotation(Sensitive.class);
            }
            // 如果能得到注解，就将注解的 value 传入 SensitiveSerialize
            if (sensitive != null) {
                return new SensitiveSerialize(sensitive.value());
            }
            return provider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return provider.findNullValueSerializer(beanProperty);
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        System.out.println("v:" + value);
        switch (this.type) {
            case CARD_NO:
                jsonGenerator.writeString(SensitiveUtil.cardNo(String.valueOf(value)));
                break;
            case TELL_PHONE:
                jsonGenerator.writeString(SensitiveUtil.tellPhone(String.valueOf(value)));
                break;
        }
    }
}
