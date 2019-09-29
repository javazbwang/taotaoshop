package com.taotao.order.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Order;
import com.taotao.pojo.OrderItem;
import com.taotao.pojo.OrderShipping;

public interface OrderService {

	TaotaoResult createOrder(Order order, List<OrderItem> itemList, OrderShipping orderShipping);

}
