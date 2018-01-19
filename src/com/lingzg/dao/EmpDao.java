package com.lingzg.dao;

import org.springframework.stereotype.Repository;

import com.lingzg.base.HibernateBaseDao;
import com.lingzg.entity.Emp;

@Repository
public class EmpDao extends HibernateBaseDao<Emp,Integer>{

	@Override
	public Class<?> getEntityClass() {
		return Emp.class;
	}
	
	
}
