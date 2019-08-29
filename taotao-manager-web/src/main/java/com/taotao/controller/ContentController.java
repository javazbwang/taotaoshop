package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Content;
import com.taotao.service.ContentService;

/**
 * 内容展示
 * @ClassName: ContentController.java
 * @version: v1.0.0
 * @author: dwg
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(Content content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}
}
