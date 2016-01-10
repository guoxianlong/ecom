package com.ecom.dto;

import java.util.List;

/**
 * 统一controller层数据封装
 * 分页数据封装
 * @author lingkong
 *
 */
public class PageDataDTO {
	/**
	 * 页码
	 */
	private int pageNo;
	/**
	 * 每页条数
	 */
	private int pageSize;
	/**
	 * 总条数
	 */
	private int pageCount;
	/**
	 * 总页数
	 */
	private int totalPageNo;
	/**
	 * 分页数据
	 */
	private List<?> list;
	
	public PageDataDTO(PageParamDTO pageParam){
		this.pageNo = pageParam.getPageNo();
		this.pageSize = pageParam.getPageSize();
		this.pageCount = pageParam.getPageCount();
		this.totalPageNo = pageParam.getTotalPageNo();
	}
	public PageDataDTO(PageParamDTO pageParam, List<?> data){
		this.pageNo = pageParam.getPageNo();
		this.pageSize = pageParam.getPageSize();
		this.pageCount = pageParam.getPageCount();
		this.totalPageNo = pageParam.getTotalPageNo();
		this.list = data;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public int getTotalPageNo() {
		return totalPageNo;
	}
	public void setTotalPageNo(int totalPageNo) {
		this.totalPageNo = totalPageNo;
	}
}
