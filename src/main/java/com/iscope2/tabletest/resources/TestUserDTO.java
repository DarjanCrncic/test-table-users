package com.iscope2.tabletest.resources;
import java.util.List;

public class TestUserDTO {
	
	private List<TestUser> data;
	private int total;
	private int pages;
	
	public TestUserDTO(List<TestUser> data, int total, int pages) {
		super();
		this.data = data;
		this.total = total;
		this.pages = pages;
	}
	
	public List<TestUser> getData() {
		return data;
	}
	public void setData(List<TestUser> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	
}
