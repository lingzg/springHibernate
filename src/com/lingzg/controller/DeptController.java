package com.lingzg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lingzg.entity.Dept;
import com.lingzg.service.DeptService;

@Controller
@RequestMapping("dept")
public class DeptController {

	@Resource
	private DeptService service;
	
	@RequestMapping("list")
	public String findAll(ModelMap model){
		List<Dept> depts = service.findAll();
		model.addAttribute("depts", depts);
		return "dept";
	}
}
