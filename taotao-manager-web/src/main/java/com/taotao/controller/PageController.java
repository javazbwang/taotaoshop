package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 * @ClassName: PageController.java
 * @version: v1.0.0
 * @author: dwg
 */
@Controller
public class PageController {

	/**
	 * 打开首页
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
		
	}
	/**
	 * 打开其他页
	 * @Function: PageController.java
	 * @author:dwg
	 * @param:描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
