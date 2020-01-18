package com.gpnews.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
public class JsonUtil {
    public static final ObjectMapper INSTANCE = new ObjectMapper();
    public static final ObjectMapper PASCAL_CASE_INSTANCE;

    public JsonUtil() {
    }

    public static String serialize(Object object) {
        try {
            String value = INSTANCE.writeValueAsString(object);
            return value;
        } catch (JsonProcessingException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        Object object = null;

        try {
            object = INSTANCE.readValue(json, TypeFactory.rawClass(clazz));
            return (T) object;
        } catch (JsonProcessingException var4) {
            throw new RuntimeException(var4);
        } catch (IOException var5) {
            throw new UncheckedIOException(var5);
        }
    }

    static {
        INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PASCAL_CASE_INSTANCE = new ObjectMapper();
        PASCAL_CASE_INSTANCE.setPropertyNamingStrategy(new PropertyNamingStrategy.PropertyNamingStrategyBase() {
            @Override
            public String translate(String propertyName) {
                return StringUtils.capitalize(propertyName);
            }
        });
    }
}
