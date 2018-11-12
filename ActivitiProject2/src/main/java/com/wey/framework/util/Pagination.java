package com.wey.framework.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Pagination implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_PAGE_INDEX = 1;
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	private int pageIndex;
	private int pageSize;
	private int rowTotal;
	private int pageTotal;
	private List datas;
	private int offset;
	private Object search;
	private boolean enabledFlag = true;
	
	public Pagination(){
		this(DEFAULT_PAGE_INDEX,DEFAULT_PAGE_SIZE);
	}
	
	public Pagination(int pageIndex,int pageSize){
		setPageIndex(pageIndex);
		setPageSize(pageSize);
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = Math.max(pageIndex, 0);
		if(this.pageTotal>0  && this.pageIndex==0){
			this.pageIndex = 1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(int rowTotal) {
		this.rowTotal = Math.max(rowTotal, 0);
		if(this.pageSize>0){
			this.pageTotal = (int)Math.ceil(this.rowTotal / (this.pageSize*1.0));
		}
		
		setPageIndex(Math.min(this.pageIndex, this.pageTotal));
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas= (datas == null?Collections.EMPTY_LIST:datas);
	}

	public int calculateOffset(){
		if(this.pageIndex>0){
			setOffset((this.pageIndex-1)*this.pageSize);
		}
		return getOffset();
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public Object getSearch() {
		return search;
	}

	public void setSearch(Object search) {
		this.search = search;
	}

	public boolean isEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(boolean enabledFlag) {
		this.enabledFlag = enabledFlag;
		if(!this.enabledFlag){
			setPageSize(0);
		}
	}
	
	
  
}
