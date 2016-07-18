package com.manu.narvar.model;

import java.util.List;

public class CountAndList<T> {

	private int count;
	private List<T> items;
	
	public CountAndList(int count, List<T> list) {
		this.count=count;
		this.items= list;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @return the items
	 */
	public List<T> getItems() {
		return items;
	}
	
	
}
