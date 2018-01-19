package com.lingzg.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lingzg.base.BaseService;
import com.lingzg.base.IBaseDao;
import com.lingzg.common.PageInfo;
import com.lingzg.common.ParamsTable;
import com.lingzg.dao.EmpDao;
import com.lingzg.entity.Emp;

@Service
public class EmpService extends BaseService<Emp, Integer>{

	@Resource
	private EmpDao dao;
	
	@Override
	protected IBaseDao<Emp, Integer> getEntityDao() {
		return dao;
	}
	
	public PageInfo findPage(ParamsTable pt){
		PageInfo page = new PageInfo(pt);
//		String hql="from Emp";
		String sql="select * from t_emp";
		List<Object> params = new ArrayList<Object>();
//		dao.findPageByHql(page,hql,params.toArray());
		dao.findPageBySql(page,sql,params.toArray());
		return page;
	}
}
