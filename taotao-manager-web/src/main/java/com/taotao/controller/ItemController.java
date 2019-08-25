package com.taotao.controller;
/**
  * 商品管理controller
 * @ClassName: ItemController.java
 * @version: v1.0.0
 * @author: dwg
 */

import org.joda.time.format.PeriodPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.PageResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Item;
import com.taotao.service.ItemService;
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	/**
	 * 获取某个商品
	 * @author dwg
	 * @param itemId
	 * @return Item
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public Item getItemById(@PathVariable Long itemId) {
		Item item = itemService.getItemById(itemId);
		System.out.println(item);
		return item;
	}
	/**
	 * 获取所有商品，分页
	 * @author dwg
	 * @param page
	 * @param rows
	 * @return PageResult
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public PageResult getItemList(Integer page, Integer rows) {
		PageResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value="/item/save",method = RequestMethod.POST)
	@ResponseBody
	private TaotaoResult createItem(Item item) {
		TaotaoResult result = itemService.createItem(item);
		return result;
	}
	
	
}
