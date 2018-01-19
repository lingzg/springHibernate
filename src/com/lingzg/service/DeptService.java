package com.lingzg.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lingzg.base.BaseService;
import com.lingzg.base.IBaseDao;
import com.lingzg.dao.DeptDao;
import com.lingzg.entity.Dept;

@Service
public class DeptService extends BaseService<Dept, Integer>{

	@Resource
	private DeptDao dao;
	
	@Override
	protected IBaseDao<Dept, Integer> getEntityDao() {
		return dao;
	}
}
