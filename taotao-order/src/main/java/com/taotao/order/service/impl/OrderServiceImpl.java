package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.OrderItemMapper;
import com.taotao.mapper.OrderMapper;
import com.taotao.mapper.OrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.Order;
import com.taotao.pojo.OrderItem;
import com.taotao.pojo.OrderShipping;

/**
 * 订单
 * 
 * @ClassName: OrderServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;

	@Override
	public TaotaoResult createOrder(Order order, List<OrderItem> itemList, OrderShipping orderShipping) {
		// 向订单表中插入记录
		// 获得订单号
		String string = jedisClient.get(ORDER_GEN_KEY);
		if (StringUtils.isBlank(string)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}
		long orderId = jedisClient.incr(ORDER_GEN_KEY);
		// 补全pojo的属性
		order.setOrderId(orderId + "");
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		// 0：未评价 1：已评价
		order.setBuyerRate(0);
		// 向订单表插入数据
		orderMapper.insert(order);
		// 插入订单明细
		for (OrderItem orderItem : itemList) {
			// 补全订单明细
			// 取订单明细id
			long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			orderItem.setId(orderDetailId + "");
			orderItem.setOrderId(orderId + "");
			// 向订单明细插入记录
			orderItemMapper.insert(orderItem);
		}
		// 插入物流表
		// 补全物流表的属性
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);

		return TaotaoResult.ok(orderId);
	}

}
