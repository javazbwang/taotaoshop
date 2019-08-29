package com.taotao.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.ContentMapper;
import com.taotao.pojo.Content;
import com.taotao.pojo.ContentExample;
import com.taotao.pojo.ContentExample.Criteria;
import com.taotao.rest.service.ContentService;
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
	public List<Content> getContentList(long contentId) {
		ContentExample example=new ContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentId);
		List<Content> list = contentMapper.selectByExample(example);
		return list;
	}

}
