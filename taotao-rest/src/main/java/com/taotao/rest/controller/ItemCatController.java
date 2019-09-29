package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
/**
 * 商品分类列表
 * @ClassName: ItemCatController.java
 * @version: v1.0.0
 * @author: dwg
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value = "/itemcat/list",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getItemCatList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		//将pojo转成字符串
		String json = JsonUtils.objectToJson(catResult);
		//拼接返回值
		String result=callback+"("+json+");";
		return result;
	}
}
