package com.lingzg.util.transformer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

import com.lingzg.util.StringUtil;

public class ClassResultTransformer implements ResultTransformer {
	
	private static final long serialVersionUID = 3656105475464785060L;
	
	private Class<?> entityClass;
	public ClassResultTransformer(Class<?> converBean){
		this.entityClass=converBean;
	}
	/**
	 * 过滤结果集
	 */
	@SuppressWarnings("rawtypes")
	public List transformList(List datalist) {
		return datalist;
	}

	/**
	 * 反射赋值
	 */
	public Object transformTuple(Object[] data, String[] colmsNames){
		Object rtn=null;
		if(entityClass!=null){
			try {
				rtn=entityClass.newInstance();int i=0;
				for(String colmsName:colmsNames){
					if(data[i]!=null){
						Field field=null;
						String entityFieldName=StringUtil.toCamelCase(colmsName);
						try {
							field=entityClass.getDeclaredField(entityFieldName);
						} catch (Exception e) {
							i++;
							continue;
						}
						String entitySetMethodName=StringUtil.pareSetName(entityFieldName);
						if(field!=null&&field.getType()!=null){
							Method method=entityClass.getMethod(entitySetMethodName, field.getType());
							if(method!=null){
								String typeName=field.getType().getSimpleName();
								if("String".equals(typeName)){
									method.invoke(rtn, data[i].toString());
								}else if("Date".equals(typeName)&& data[i] instanceof Date){
									method.invoke(rtn, (Date)data[i]);
								}else if("Timestamp".equals(typeName)&& data[i] instanceof Timestamp){
									method.invoke(rtn, (Date)data[i]);
								}else if("int".equals(typeName)){
									method.invoke(rtn, Integer.valueOf(data[i].toString()));
								}else if("Integer".equals(typeName)){
									method.invoke(rtn, Integer.valueOf(data[i].toString()));
								}else if("long".equals(typeName)){
									method.invoke(rtn, Long.valueOf(data[i].toString()));
								}else if("Long".equals(typeName)){
									method.invoke(rtn, Long.valueOf(data[i].toString()));
								}else if("Double".equals(typeName)){
									method.invoke(rtn, (Double)(data[i]));
								}else if("BigDecimal".equals(typeName)){
									if(data[i] instanceof BigDecimal){
										method.invoke(rtn,data[i]);
									}
								}
								
							}
						}
					}
					i++;

				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}
}

