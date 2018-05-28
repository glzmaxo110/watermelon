package com.xx.watermelon.common.page;


import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class PageParam implements Serializable {

	private static final long serialVersionUID = -6058969806493789590L;
	private int pageNum = 1;		//当前页数
	private int numPerPage = 15;	//每页记录数
	private String sortName;     //列表排序字段名
	private String sortType;	 //列表排序类型:ACS或DESC

	/**
	 * @param pageNum		当前页数
	 * @param numPerPage	每页记录数
	 */
	public PageParam(int pageNum, int numPerPage) {
		super();
		if(pageNum > 0) {
			this.pageNum = (pageNum / numPerPage) + 1;
		}
		this.numPerPage = numPerPage;
	}
	
	/**
	 * @param pageNum		当前页数
	 * @param numPerPage	每页记录数
	 * @param sortName		排序条件
	 * @param numPerPage	排序类型
	 */
	public PageParam(int pageNum, int numPerPage, String sortName, String sortType) {
		super();
		if(pageNum > 0) {
			this.pageNum = (pageNum / numPerPage) + 1;
		}
		this.numPerPage = numPerPage;
		this.sortName = StringUtils.isNotBlank(sortName) ? sortName : "lastModifyTime" ;
		this.sortType = StringUtils.isNotBlank(sortType) ? sortType : "DESC" ;
	}

	/**
	 * 当前页数
	 */
	public int getPageNum() {
		return pageNum;
	}
	/**
	 * 当前页数
	 */
	public void setPageNum(int pageNum) {
		if(pageNum > 0)this.pageNum = pageNum;
	}

	/**
	 * 每页记录数
	 */
	public int getNumPerPage() {
		return numPerPage;
	}
	/**
	 * 每页记录数
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	/**
	 * 列表排序字段名
	 */
	public String getSortName() {
		return sortName;
	}
	/**
	 * 列表排序字段名
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	/**
	 * 列表排序类型:ACS或DESC
	 */
	public String getSortType() {
		return sortType;
	}
	/**
	 * 列表排序类型:ACS或DESC
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("pageNum").append("='").append(getPageNum()).append("' ");
		buffer.append("numPerPage").append("='").append(getNumPerPage()).append("' ");
		buffer.append("sortName").append("='").append(getSortName()).append("' ");
		buffer.append("sortType").append("='").append(getSortType()).append("' ");
		buffer.append("]");
		return buffer.toString();
	}
}
