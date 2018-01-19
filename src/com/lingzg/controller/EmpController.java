package com.lingzg.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lingzg.common.PageInfo;
import com.lingzg.common.ParamsTable;
import com.lingzg.entity.Emp;
import com.lingzg.service.EmpService;

@Controller
@RequestMapping("/emp")
public class EmpController {

	@Resource
	private EmpService service;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		ParamsTable params = ParamsTable.convertHTTP(request);
		Object mgr = params.getParameter("mgr");
		if(mgr!=null){
			params.setParameter("mgr", Integer.parseInt(mgr.toString()));
		}
		List<Emp> emps = service.findByParamsTable(params);
		model.addAttribute("emps", emps);
		return "emp";
	}
	
	@RequestMapping("/page")
	public String page(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		ParamsTable params = ParamsTable.convertHTTP(request);
		PageInfo page = service.findPage(params);
		model.addAttribute("emps", page.getRows());
		return "emp";
	}
}
