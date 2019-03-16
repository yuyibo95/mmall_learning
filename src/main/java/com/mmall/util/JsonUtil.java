package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;

/**
 * Created by yyb on 3/10/19.
 */
@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper=new ObjectMapper();
    static {

        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);

    }

    public static <T>String obj2String(T obj){
        if(obj==null){
            return null;
        }

        try {

            return obj instanceof String ? (String) obj:objectMapper.writeValueAsString(obj);
        }catch (Exception e){
            log.warn("Parse object to string error",e);
            return null;
        }
    }

    public static <T>String obj2StringPretty(T obj){
        if(obj==null){
            return null;
        }

        try {

            return obj instanceof String ? (String) obj:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }catch (Exception e){
            log.warn("Parse object to string error",e);
            return null;
        }
    }



    public static <T> T string2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str)||clazz==null){
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T)str:objectMapper.readValue(str,clazz);
        }catch (Exception e){
            log.warn("Prase String to object erroe",e);
            return null;
        }


    }


    public static <T> T string2Obj(String str, TypeReference<T> tTypeReference){
        if(StringUtils.isEmpty(str)||tTypeReference==null){
            return null;
        }

        try {
            return (T)(tTypeReference.getType().equals(String.class)?str :objectMapper.readValue(str,tTypeReference));
        }catch (Exception e){
            log.warn("Prase String to object erroe",e);
            return null;
        }

    }



    public static <T> T string2Obj(String str, Class<?> collectionClass,Class<?>...elementClasses){

        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);


        try {
            return objectMapper.readValue(str,javaType);
        }catch (Exception e){
            log.warn("Prase String to object erroe",e);
            return null;
        }

    }





}
