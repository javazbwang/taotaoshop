package com.taotao.common.utils;
/**
 * 自定义响应结构
 * @ClassName: JsonUtils.java
 * @version: v1.0.0
 * @author: dwg
 */

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	//定义jackson对象
	private static final ObjectMapper MAPPER=new ObjectMapper();
	
	/**
	 * 将对象转成Json字符串
	 * @author dwg
	 * @param data
	 * @return String
	 */
	public static String  objectToJson(Object data) {
		try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * json字符串转对象
	 * @author dwg
	 * @param <T>
	 * @param jsonData
	 * @param beanType
	 * @return T
	 */
	public static <T> T jsonToPojo(String jsonData,Class<T> beanType) {
		try {
			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将json字符串转成pojo对象list
	 * @author dwg
	 * @param <T>
	 * @param jsonData
	 * @param beanType
	 * @return List<T>
	 */
	public static <T> List<T> jsonToList(String jsonData,Class<T> beanType) {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class,beanType);
		try {
			List<T> list = MAPPER.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
