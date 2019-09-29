package com.taotao.portal.pojo;

import java.util.List;

import com.taotao.pojo.OrderItem;
import com.taotao.pojo.Order;
import com.taotao.pojo.OrderShipping;
/**
 * 接收参数
 * @ClassName: Order_o.java
 * @version: v1.0.0
 * @author: dwg
 */
public class Order_o extends Order {

	private List<OrderItem> orderItems;
	private OrderShipping orderShipping;
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
	
}
