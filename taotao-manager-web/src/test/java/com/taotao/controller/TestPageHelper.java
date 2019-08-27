package com.taotao.controller;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemExample;

public class TestPageHelper {
	@Test
	public void testPageHelper() {
//		创建spring容器
		ApplicationContext applicationContext= new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
//		从容器中获取代理对象
		ItemMapper mapper = applicationContext.getBean(ItemMapper.class);
//		执行查询
		ItemExample example=new ItemExample();
//		分页
		PageHelper.startPage(2, 10);
		List<Item> list = mapper.selectByExample(example);
//		查询列表信息
		for (Item item : list) {
			System.out.println(item.getTitle());
		}
//		获取分页信息
		PageInfo<Item> pageInfo=new  PageInfo<Item>(list);
		long total = pageInfo.getTotal();
		System.out.println(total);
		
	}
}
