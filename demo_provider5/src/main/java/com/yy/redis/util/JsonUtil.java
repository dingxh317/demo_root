package com.yy.redis.util;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

public class JsonUtil {

	private final static Gson GSON = new Gson();

	public static String toJson(Object obj) {
		return GSON.toJson(obj);
	}

	public static <T> T fromJson(String json, Class<T> c) {
		if(StringUtils.isEmptyOrWhitespaceOnly(json)){
			return null;
		}
		return GSON.fromJson(json, c);
	}

}
