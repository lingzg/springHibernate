package com.lingzg.base;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lingzg.common.PageInfo;
import com.lingzg.util.transformer.CamelResultTransformer;
import com.lingzg.util.transformer.ClassResultTransformer;
import com.lingzg.util.transformer.FirstLowerCaseResultTransformer;
import com.lingzg.util.transformer.MapResultTransformer;

public abstract class HibernateBaseDao<E,PK extends Serializable> extends HibernateDaoSupport implements IBaseDao<E, PK>{

	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	protected abstract Class<?> getEntityClass();
	
	public Session getCurrentSession() {
		return this.getSessionFactory().getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findAll(){
		String hql="from "+getEntityClass().getSimpleName();
		return getHibernateTemplate().find(hql);
	}
	
	public List<E> findByProperty(String propertyName, Object value){
		return findByProperties(new String[]{propertyName}, new Object[]{value});
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findByProperties(String[] propertyNames, Object[] values){
		StringBuffer hql=new StringBuffer();
		hql.append("from ").append(getEntityClass().getSimpleName());
		if(propertyNames!=null){
			for(int i=0;i<propertyNames.length;i++){
				hql.append(i==0 ? " where " : " and ");
				hql.append(propertyNames[i]).append("=:").append(propertyNames[i]);
			}
		}
		return getHibernateTemplate().findByNamedParam(hql.toString(), propertyNames, values);
	}
	
	public List<?> findByCriteria(DetachedCriteria criteria){
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public E get(PK id){
		return (E) getHibernateTemplate().get(getEntityClass(), id);
	}
	
	public void save(E entity){
		getHibernateTemplate().save(entity);
	}
	
	public void update(E entity){
		getHibernateTemplate().update(entity);
	}
	
	public void delete(E entity){
		getHibernateTemplate().delete(entity);
	}
	
	public void delete(PK id){
		E dept = get(id);
		if(dept == null) {
			throw new ObjectRetrievalFailureException(getEntityClass(),id);
		}
		getHibernateTemplate().delete(dept);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findListByHql(String hql,Object... params){
		Query query=getCurrentSession().createQuery(hql);
		if(params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		List<?> list=query.list();
		return (List<E>) list;
	}
	
	public List<?> findListBySql(String sql,Object[] params,ResultTransformer transformer){
		SQLQuery query=getCurrentSession().createSQLQuery(sql);
		if(params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		if(transformer!=null){
			query.setResultTransformer(transformer);
		}
		List<?> list=query.list();
		return list;
	}
	
	public List<?> findListBySql(String sql,Object[] params,Class<?> clazz){
		ResultTransformer transformer=null;
		if(clazz!=null){
			transformer=new ClassResultTransformer(clazz);
		}
		return findListBySql(sql,params,transformer);
	}
	
	public List<?> findEntityListBySql(String sql,Object[] params,Class<?> clazz){
		SQLQuery query=getCurrentSession().createSQLQuery(sql);
		if(params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		if(clazz!=null){
			query.addEntity(clazz);
		}
		List<?> list=query.list();
		return list;
	}
	
	public List<?> findListBySql(String sql,Object... params){
		ResultTransformer transformer=null;
		return findListBySql(sql,params,transformer);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapListBySql(String sql,Object... params){
		return (List<Map<String, Object>>) findListBySql(sql,params,new MapResultTransformer());
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapListBySql2(String sql,Object... params){
		return (List<Map<String, Object>>) findListBySql(sql,params,new CamelResultTransformer());
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapListBySql3(String sql,Object... params){
		return (List<Map<String, Object>>) findListBySql(sql,params,new FirstLowerCaseResultTransformer());
	}
	
	public void findPageByHql(PageInfo page,String hql,Object... params){
		Session session=getCurrentSession();
		int fromIndex =hql.indexOf("from");
	    String counthql = "select count(*) "+hql.substring(fromIndex); 
		Query queryCount=session.createQuery(counthql);
		Query query=session.createQuery(hql);
		if(params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
				queryCount.setParameter(i, params[i]);
			}
		}
		long totalCount =((Long)queryCount.uniqueResult()).longValue();  
		page.setTotal(totalCount);
		query.setFirstResult(page.getStartRow());
		query.setMaxResults(page.getPageSize());
		List<?> list=query.list();
		page.setRows(list);
	}
	
	public void findPageBySql(PageInfo page, String sql, Object[] params,ResultTransformer transformer){
		Session session=getCurrentSession();
	    String countSql = "select count(1) from("+sql+")tcont";
		SQLQuery queryCount = session.createSQLQuery(countSql);
		SQLQuery query = session.createSQLQuery(sql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				queryCount.setParameter(i, params[i]);
				query.setParameter(i, params[i]);
			}
		}
		long count = ((BigInteger) queryCount.uniqueResult()).longValue();
		page.setTotal(count);
		query.setMaxResults(page.getPageSize());
		query.setFirstResult(page.getStartRow());
		query.setResultTransformer(new MapResultTransformer());
		List<?> list = query.list();
		page.setRows(list);
	}
	
	public void findPageBySql(PageInfo page, String sql, Object... params){
		findPageBySql(page, sql, params, new MapResultTransformer());
	}
	
	public void findPageBySql2(PageInfo page, String sql, Object... params){
		findPageBySql(page, sql, params, new CamelResultTransformer());
	}
	
	public void findPageBySql3(PageInfo page, String sql, Object... params){
		findPageBySql(page, sql, params, new FirstLowerCaseResultTransformer());
	}
	
	public void findPageByCriteria(PageInfo page, DetachedCriteria dc){
		Session session=getCurrentSession();
		Criteria c = dc.getExecutableCriteria(session);
		page.setTotal(((Long) c.setProjection(Projections.rowCount()).uniqueResult()).intValue());
		c.setProjection(null);
		c.setResultTransformer(Criteria.ROOT_ENTITY);
		c.setFirstResult(page.getStartRow());
		c.setMaxResults(page.getPageSize());
		if("ASC".equalsIgnoreCase(page.getOrderFlag())){
			dc.addOrder(Order.asc(page.getOrder()));
		}else if("DESC".equalsIgnoreCase(page.getOrderFlag())){
			dc.addOrder(Order.desc(page.getOrder()));
		}
		page.setRows(c.list());
	}
	
	public boolean executeSql(String sql, Object... params){
		Session session=getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		query.executeUpdate();
		return true;
	}
}
