package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.PageResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.ItemDescMapper;
import com.taotao.mapper.ItemMapper;
import com.taotao.mapper.ItemParamItemMapper;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.pojo.ItemExample;
import com.taotao.pojo.ItemParamItem;
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
	@Autowired
	ItemDescMapper itemDescMapper;
	@Autowired
	ItemParamItemMapper itemParamItemMapper;
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
	/**
	 * 添加商品
	 * @Function createItem
	 * @author dwg
	 * @param item
	 * @return 
	 * @throws Exception 
	 * @see com.taotao.service.ItemService#createItem(com.taotao.pojo.Item)
	 */
	@Override
	public TaotaoResult createItem(Item item, String desc,String itemParam) throws Exception {
		//生成商品id
		Long itemId=IDUtils.genItemId();
		item.setId(itemId);
		//商品状态 1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		//添加商品描述信息
		TaotaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		//添加规格参数信息
		result = insertItemParamItem(itemId,itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		
		return TaotaoResult.ok();
	}
	/**
	 * 添加商品描述
	 * @author dwg
	 * @param itemId
	 * @param desc
	 * @return TaotaoResult
	 */
	private TaotaoResult insertItemDesc(Long itemId, String desc) {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}

	/**
	 * 添加规格参数
	 * @author dwg
	 * @param itemId
	 * @param itemParam
	 * @return TaotaoResult
	 */
	private TaotaoResult insertItemParamItem(Long itemId, String itemParam) {
		//创建一个pojo
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		//向表中插入数据
		itemParamItemMapper.insert(itemParamItem);
		
		return TaotaoResult.ok();
		
	}


}
