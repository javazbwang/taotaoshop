package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.PageResult;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemExample;
import com.taotao.pojo.ItemExample.Criteria;
import com.taotao.service.ItemService;
/**
 * 商品管理serviceimpl
 * @ClassName: ItemServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	ItemMapper itemMapper;
	/**
	 * 通过id获取商品
	 * @Function getItemById
	 * @author dwg
	 * @param itemId
	 * @return 
	 * @see com.taotao.service.ItemService#getItemById(long)
	 */
	@Override
	public Item getItemById(long itemId) {
//		Item selectByPrimaryKey = itemMapper.selectByPrimaryKey(itemId);
		ItemExample ex= new ItemExample();
		Criteria criteria = ex.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<Item> list = itemMapper.selectByExample(ex);
		if (list != null&&list.size()>0) {
			Item item = list.get(0);
			return item;
		}
		return null;
	}
	/**
	 * 分页查询商品
	 * @Function getItemList
	 * @author dwg
	 * @param page
	 * @param rows
	 * @return 
	 * @see com.taotao.service.ItemService#getItemList(int, int)
	 */
	@Override
	public PageResult getItemList(int page, int rows) {
		ItemExample example=new ItemExample();
		PageHelper.startPage(page,rows);
		List<Item> list = itemMapper.selectByExample(example);
		PageResult result=new PageResult();
		result.setRows(list);
		PageInfo<Item> pageInfo=new PageInfo<Item>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

}
