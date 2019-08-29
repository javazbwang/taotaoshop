package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.pojo.ContentCategory;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类
 * @ClassName: ContentCategoryController.java
 * @version: v1.0.0
 * @author: dwg
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<TreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") long parentId) {
		List<TreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
		
	}
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId,String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result; 
	}
}
