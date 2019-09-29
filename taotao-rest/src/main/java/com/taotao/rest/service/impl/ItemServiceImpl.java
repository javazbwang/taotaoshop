package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.ItemDescMapper;
import com.taotao.mapper.ItemMapper;
import com.taotao.mapper.ItemParamItemMapper;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.pojo.ItemParamItem;
import com.taotao.pojo.ItemParamItemExample;
import com.taotao.pojo.ItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
/**
 * 商品信息
 * @ClassName: ItemServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Autowired
	private JedisClient jedisClient;
	/**
	 * 获取商品基本信息
	 * @Function getItemBaseInfo
	 * @author dwg
	 * @param itemId
	 * @return 
	 * @see com.taotao.rest.service.ItemService#getItemBaseInfo(long)
	 */
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				Item item = JsonUtils.jsonToPojo(json, Item.class);
				return TaotaoResult.ok(item);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//根据商品id查询商品信息
		Item item = itemMapper.selectByPrimaryKey(itemId);
		//使用TaotaoResult包装一下
		try {
			//把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
			//设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}
	/**
	 * 获取商品描述信息
	 * @Function getItemDesc
	 * @author dwg
	 * @param itemId
	 * @return 
	 * @see com.taotao.rest.service.ItemService#getItemDesc(long)
	 */
	@Override
	public TaotaoResult getItemDesc(long itemId) {
		//添加缓存
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				ItemDesc itemDesc = JsonUtils.jsonToPojo(json, ItemDesc.class);
				return TaotaoResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//创建查询条件
		ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		try {
			//把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			//设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok(itemDesc);
	}
	/**
	 * 获取商品规格信息
	 * @Function getItemParam
	 * @author dwg
	 * @param itemId
	 * @return 
	 * @see com.taotao.rest.service.ItemService#getItemParam(long)
	 */
	@Override
	public TaotaoResult getItemParam(long itemId) {
		//添加缓存
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				ItemParamItem paramItem = JsonUtils.jsonToPojo(json, ItemParamItem.class);
				return TaotaoResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询规格参数
		//设置查询条件
		ItemParamItemExample example = new ItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<ItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size()>0) {
			ItemParamItem paramItem = list.get(0);
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(paramItem);
		}
		return TaotaoResult.build(400, "无此商品规格");
	}


}
