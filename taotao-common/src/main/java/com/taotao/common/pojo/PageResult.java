package com.taotao.common.pojo;

import java.util.List;
/**
 * 分页
 * @ClassName: PageResult.java
 * @version: v1.0.0
 * @author: dwg
 */
public class PageResult {
	private long total;
	private List<?> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
