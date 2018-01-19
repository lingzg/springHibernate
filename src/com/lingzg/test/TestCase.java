package com.lingzg.test;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lingzg.dao.DeptDao;
import com.lingzg.dao.EmpDao;
import com.lingzg.entity.Dept;
import com.lingzg.entity.Emp;

public class TestCase {
	
//	@Test
	public void test1() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		EmpDao dao = ctx.getBean("empDao", EmpDao.class);
		List<Emp> list = dao.findAll();
		for(Emp e : list) {
			System.out.println(e.getEmpno() + " " +e.getEname());
		}
	}
	
//	@Test
	public void test2() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		EmpDao dao = ctx.getBean("empDao", EmpDao.class);
		Emp e = dao.get(1);
		System.out.println(e.getEmpno() + " " +e.getEname());
	}
	
//	@Test
	public void test3() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		EmpDao dao = ctx.getBean("empDao", EmpDao.class);
		Emp e = new Emp();
		e.setEname("tom");
		dao.save(e);
	}
	
//	@Test
	public void test4() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		EmpDao dao = ctx.getBean("empDao", EmpDao.class);
		Emp e = dao.get(15);
		e.setJob("salesman");
		dao.update(e);
	}
	
//	@Test
	public void test5() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		EmpDao dao = ctx.getBean("empDao", EmpDao.class);
		dao.delete(new Emp());
	}
	
//	@Test
	public void test6() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		DeptDao dao = ctx.getBean("deptDao", DeptDao.class);
		Dept d = dao.get(10);
		System.out.println(d.getDeptno() + " " +d.getDname());
	}
	
	@Test
	public void test7() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		DeptDao dao = ctx.getBean("deptDao", DeptDao.class);
		List<?> list = dao.findListByHql("from Dept");
		for(Object de : list){
			Dept d = (Dept) de;
			System.out.println(d.getDeptno() + " " +d.getDname());
		}
	}

}
