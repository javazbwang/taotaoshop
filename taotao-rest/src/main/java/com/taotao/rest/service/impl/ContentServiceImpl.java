package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.ContentMapper;
import com.taotao.pojo.Content;
import com.taotao.pojo.ContentExample;
import com.taotao.pojo.ContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;

import redis.clients.jedis.Jedis;
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
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Override
	public List<Content> getContentList(long contentId) {
		//从缓存 中获取内容
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentId+"");
			if (!StringUtils.isBlank(result)) {
				//把字符串转换成list
				List<Content> resultList = JsonUtils.jsonToList(result, Content.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//根据内容分类id查询列表
		ContentExample example=new ContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentId);
		List<Content> list = contentMapper.selectByExample(example);
		
		//向缓存中添加内容
		try {
			//先把list转换成字符串
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentId+"", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
