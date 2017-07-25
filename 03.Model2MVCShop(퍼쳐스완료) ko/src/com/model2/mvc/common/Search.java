package com.model2.mvc.common;


public class Search {
	
	private int currentPage;
	private String searchCondition;
	private String searchKeyword;
	private int pageSize;
	
	public Search(){
	}
	
	public int getPageUnit() {
		return pageSize;
	}
	public void setPageUnit(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	@Override
	public String toString() {
		return "Search [currentPage=" + currentPage + ", searchCondition=" + searchCondition + ", searchKeyword="
				+ searchKeyword + ", pageSize=" + pageSize + "]";
	}
	
	
}