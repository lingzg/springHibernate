package com.lingzg.util.transformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;

public class FirstLowerCaseResultTransformer implements ResultTransformer {

	private static final long serialVersionUID = -6868377196529580759L;

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List data) {
		return data;
	}

	@Override
	public Object transformTuple(Object[] data, String[] colmsNames) {
		Map<String, Object> objMap=new HashMap<String, Object>();
		for(int i=0;i<colmsNames.length;i++){
			String field =colmsNames[i].toString();
			int index=0;
			while(Character.isUpperCase(field.charAt(index))){
				field = field.substring(0,index)+Character.toLowerCase(field.charAt(index))+field.substring(index+1);
				index++;
			}
			objMap.put(field, data[i]);
		}
		return objMap;
	}

}
