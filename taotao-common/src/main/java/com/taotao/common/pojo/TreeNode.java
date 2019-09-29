package com.taotao.common.pojo;
/**
 * 树形节点格式
 * @ClassName: TreeNode.java
 * @version: v1.0.0
 * @author: dwg
 */
public class TreeNode {
	private long id;
	private String text;
	private String state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
