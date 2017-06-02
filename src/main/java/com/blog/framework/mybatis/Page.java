package com.blog.framework.mybatis;

import java.util.List;

public class Page<T> {

	private Integer currentPage = 1;
	private Integer pageSize = 10;
	private Integer totalPage;
	private Long totalCount;

	private Integer limitStart;

	private List<T> rows;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		if (totalCount % pageSize == 0)
			totalPage = totalCount.intValue() / pageSize;
		else
			totalPage = totalCount.intValue() / pageSize + 1;
	}

	public Integer getLimitStart() {
		limitStart = (currentPage - 1) * pageSize;
		return limitStart;
	}

	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}