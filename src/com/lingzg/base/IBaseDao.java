package com.lingzg.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.ResultTransformer;

import com.lingzg.common.PageInfo;

public interface IBaseDao<E,PK extends Serializable> {
	
	List<E> findAll();
	
	List<E> findByProperty(String propertyName, Object value);
	
	List<E> findByProperties(String[] propertyNames, Object[] values);
	
	E get(PK id);

	void save(E entity);
	
	void update(E entity);
	
	void delete(PK id);
	
	void delete(E entity);
	
	List<E> findListByHql(String hql,Object... params);
	
	List<?> findListBySql(String sql,Object[] params,ResultTransformer transformer);
	
	List<?> findListBySql(String sql,Object[] params,Class<?> clazz);
	
	List<?> findEntityListBySql(String sql,Object[] params,Class<?> clazz);
	
	List<?> findListBySql(String sql,Object... params);
	
	List<Map<String, Object>> findMapListBySql(String sql,Object... params);
	
	List<Map<String, Object>> findMapListBySql2(String sql,Object... params);
	
	List<Map<String, Object>> findMapListBySql3(String sql,Object... params);
	
	void findPageByHql(PageInfo page,String hql,Object... params);
	
	void findPageBySql(PageInfo page, String sql, Object[] params,ResultTransformer transformer);
	
	void findPageBySql(PageInfo page, String sql, Object... params);
	
	void findPageBySql2(PageInfo page, String sql, Object... params);
	
	void findPageBySql3(PageInfo page, String sql, Object... params);
	
	void findPageByCriteria(PageInfo page, DetachedCriteria dc);
	
	boolean executeSql(String sql, Object... params);

}
