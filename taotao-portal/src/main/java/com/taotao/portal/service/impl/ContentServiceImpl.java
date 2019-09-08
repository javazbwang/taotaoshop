package com.taotao.portal.service.impl;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.Content;
import com.taotao.portal.service.ContentService;
/**
 * 调用服务层，查询内容列表
 * @ClassName: ContentServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
	@Override
	public String getContentList() {
		//调用服务层的服务
		String result = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
//		System.out.println(result+"----------------------");
//		将字符串转换成TaoTaoResult
		try {
			TaotaoResult taotaoResult = TaotaoResult.formatToList(result, Content.class);
//			获取内容列表
//			System.out.println(taotaoResult.toString()+"++++++++++++");
			List<Content> list=(List<Content>) taotaoResult.getData();
			List<Map> resultList=new ArrayList<>();
//			创建一个符合jsp页的pojo列表
			for (Content content : list) {
				Map map =new HashMap<>();
				map.put("src",content.getPic());
				map.put("height",240);
				map.put("width",670);
				map.put("srcB",content.getPic2());
				map.put("widthB",550);
				map.put("heightB",220);
				map.put("href",content.getUrl());
				map.put("alt",content.getSubTitle());
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
