package com.zf.zson.result.utils;

import java.util.List;
import java.util.Map;

import com.zf.zson.ZsonUtils;

public class ZsonResultToString {
	
	private String listToString(List<Object> list){
		StringBuffer sb = new StringBuffer();
		sb.append(ZsonUtils.jsonListBegin);
		int index = 0;
		if(list!=null){
			for (Object element : list) {
				if(index!=0){
					sb.append(ZsonUtils.jsonElementConnector);
				}
				if(element instanceof Map || element instanceof List){
					String mapString = this.toJsonString(element);
					sb.append(mapString);
				}else if(element instanceof String){
					sb.append(ZsonUtils.jsonStringBegin);
					sb.append(element);
					sb.append(ZsonUtils.jsonStringEnd);
				}else{
					sb.append(element);
				}
				index++;
			}
		}
		sb.append(ZsonUtils.jsonListEnd);
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String toJsonString(Object obj){
		if(obj instanceof Map){
			return this.mapToString((Map<Object, Object>) obj);
		}else if(obj instanceof List){
			return this.listToString((List<Object>) obj);
		}else{
			throw new RuntimeException("obj must be map or list!");
		}
	}
	
	private String mapToString(Map<Object, Object> map){
		StringBuffer sb = new StringBuffer();
		sb.append(ZsonUtils.jsonMapBegin);
		int index = 0;
		if(map!=null){
			for (Object key : map.keySet()) {
				if(!(key instanceof String)){
					throw new RuntimeException("map key must be string!");
				}
				if(index!=0){
					sb.append(ZsonUtils.jsonElementConnector);
				}
				sb.append(ZsonUtils.jsonStringBegin);
				sb.append(key);
				sb.append(ZsonUtils.jsonStringEnd);
				sb.append(ZsonUtils.jsonMapConnector);
				if(map.get(key) instanceof Map || map.get(key) instanceof List){
					String mapString = this.toJsonString(map.get(key));
					sb.append(mapString);
				}else if(map.get(key) instanceof String){
					sb.append(ZsonUtils.jsonStringBegin);
					sb.append(map.get(key));
					sb.append(ZsonUtils.jsonStringEnd);
				}else{
					sb.append(map.get(key));
				}
				index++;
			}
		}
		sb.append(ZsonUtils.jsonMapEnd);
		return sb.toString();
	}
}
