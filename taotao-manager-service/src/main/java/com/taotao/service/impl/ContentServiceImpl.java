package com.taotao.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.ContentMapper;
import com.taotao.pojo.Content;
import com.taotao.service.ContentService;
/**
 * 内容管理
 * @ClassName: ContentServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;
	@Override
	public TaotaoResult insertContent(Content content) {
		//补全pojo
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}

}
