package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.service.ContentService;

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		String adJson = contentService.getContentList();
		System.out.println(adJson+"**********");
		model.addAttribute("ad1",adJson);
		return "index";
	}
	
	@RequestMapping(value = "/httpclient/post",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult testPost(String name,String password) {
		String result= name+"***"+password;
		System.out.println(result);
		return TaotaoResult.ok(result);
	}
}
