package com.example.demo.utils;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * JSON转换工具类
 *
 * @author XuDongDong  2015/4/15.
 */
public class JsonUtility {
    public JsonUtility() {
    }

    public static <T> String objectToJson(T source) throws IOException {
        return getObjectMapper().writeValueAsString(source);
    }

    /**
     * Json反序列化 适用于泛型<br/>
     * 例如：<br/>
     * 1、List&lt;Foo&gt; list = jsonToObject(json,List.class,Foo.class);<br/>
     * 2、Map&lt;Foo,Boo&gt; map = jsonToObject(json,Map.class,Foo.class,Boo.class)<br/>
     *
     * @param json            json字符串
     * @param dataType        数据类型
     * @param genericityTypes 泛型类型 可以多个
     * @param <T>             泛型
     * @return 序列化后的对象
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T jsonToObject(String json, Class<?> dataType, Class<?>... genericityTypes) throws IOException, InstantiationException, IllegalAccessException {
        ObjectMapper objectMapper = getObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(dataType, genericityTypes);
        return getObjectMapper().readValue(json, type);
    }

    /**
     * Json反序列化 <br/>
     * 例如：<br/>
     * Foo foo = jsonToObject(json,Foo.class);<br/>
     *
     * @param json     json字符串
     * @param dataType 数据类型
     * @param <T>      泛型
     * @return 反序列化后的对象
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T jsonToObject(String json, Class<T> dataType) throws IOException, InstantiationException, IllegalAccessException {
        return getObjectMapper().readValue(json, dataType);
    }
    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 任何级别的字段都可以自动识别
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // 忽略Json串总有的字段而对象体中没有的
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 告诉Jackson空对象不要抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 视空字符传为null
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        // 确定JSON null是否可用于Java基元类型（int， boolean，double等）
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        // 允许出现特殊字符和转义符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许出现斜线
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return objectMapper;
    }

    /**
     * Json反序列化 适用于多层级泛型<br/>
     * 例如：<br/>
     * UniResult&lt;List&lt;JsonResult&lt;String&gt;&gt;&gt; uni = jsonToObject(json, new TypeReference&lt;UniResult&lt;List&lt;JsonResult&lt;String&gt;&gt;&gt;&gt;(){});<br/>
     *
     * @param json json字符串
     * @param ref  参照类型
     * @param <T>  泛型
     * @return 反序列化后的对象
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T jsonToObject(String json, TypeReference ref) throws IOException, InstantiationException, IllegalAccessException {
        return getObjectMapper().readValue(json, ref);
    }

}
