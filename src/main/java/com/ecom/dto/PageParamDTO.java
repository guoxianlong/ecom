package com.ecom.dto;

/**
 * 对分页参数的封装
 * @author lingkong
 *
 */
public class PageParamDTO {
	/**
	 * 页码,从1开始
	 */
	private int pageNo;
	/**
	 * 分页时，一页条数
	 */
	private int pageSize;
	/**
	 * 从数据库中检索开始条数
	 */
	private int start;
	/**
	 * 总页数
	 */
	private int totalPageNo;
	/**
	 * 总条数
	 */
	private int pageCount;
	

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStart() {
		return pageSize*pageNo - pageSize;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getTotalPageNo() {
		if(pageCount > 0){
			return (int)Math.ceil((double)pageCount/pageSize);
		}
		return 0;
	}
	public void setTotalPageNo(int totalPageNo) {
		
		this.totalPageNo = totalPageNo;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
}
