package com.lingzg.dao;

import org.springframework.stereotype.Repository;

import com.lingzg.base.HibernateBaseDao;
import com.lingzg.entity.Dept;

@Repository
public class DeptDao extends HibernateBaseDao<Dept,Integer>{

	@Override
	public Class<?> getEntityClass() {
		return Dept.class;
	}
	
	
}
