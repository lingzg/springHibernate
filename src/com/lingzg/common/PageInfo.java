package com.lingzg.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PageInfo {

	/**
	 * 页面每页记录条数
	 */
	private int pageSize;

	/**
	 * 总记录条数
	 */
	private long total;

	/**
	 * 当前页
	 */
	private int currentPage;

	/**
	 * 总页数
	 */
	private int pageTotal;

	private int startRow;

	private List<?> rows;

	private String order;

	private String orderFlag;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageTotal() {
		pageTotal = (int) (total%pageSize==0 ? total/pageSize : total/pageSize+1);
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public PageInfo() {
		this.total = 0;
		this.currentPage = 1;
		this.pageSize = 20;
		this.pageTotal = 0;
		this.rows = new ArrayList<Object>();
	}

	public PageInfo(ParamsTable params) {
		int _currentPage = params.getParameterAsInt("page");
		this.currentPage = _currentPage;
		if (_currentPage == 0) {
			this.currentPage = 1;
		}
		int _pageSize = params.getParameterAsInt("limit");
		this.pageSize = _pageSize;
		if (_pageSize == 0) {
			this.pageSize = 20;
		}
		String sorts = params.getParameterAsString("sort");
		if (!StringUtils.isBlank(sorts)) {
			JSONArray jarr = JSONArray.fromObject(sorts);
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject obj = (JSONObject) jarr.get(i);
				this.order = obj.getString("property");
				this.orderFlag = obj.getString("direction");
			}
		}

		/*
		 * sort [{"property":"apply_no","direction":"ASC"}]
		 */

	}
	
	public int getStartRow() {
		this.startRow = (this.currentPage - 1) * this.pageSize;
		return this.startRow;
	}

}
