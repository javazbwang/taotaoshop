package com.taotao.service;

import com.taotao.common.pojo.PageResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Item;
/**
 * 商品管理service
 * @ClassName: ItemService.java
 * @version: v1.0.0
 * @author: dwg
 */
public interface ItemService {
	Item getItemById(long itemId);
	PageResult getItemList(int page,int rows);
	TaotaoResult createItem(Item item, String desc, String itemParam) throws Exception;
}
