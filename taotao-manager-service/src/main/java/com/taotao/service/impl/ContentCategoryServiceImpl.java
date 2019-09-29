package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.mapper.ContentCategoryMapper;
import com.taotao.pojo.ContentCategory;
import com.taotao.pojo.ContentCategoryExample;
import com.taotao.pojo.ContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
/**
 * 内容分类
 * @ClassName: ContentCategoryServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	ContentCategoryMapper contentCategoryMapper;
	/**
	 * 根据parentId查询列表
	 * @Function getCategoryList
	 * @author dwg
	 * @param parentId
	 * @return 
	 * @see com.taotao.service.ContentCategoryService#getCategoryList(long)
	 */
	@Override
	public List<TreeNode> getCategoryList(long parentId) {
		//根据parentid查询节点列表
		ContentCategoryExample example=new ContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<ContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<TreeNode> resultList = new ArrayList<>();
		for(ContentCategory contentCategory:list) {
			//创建一个节点
			TreeNode node=new TreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			
			resultList.add(node);
		}
		return resultList;
	}
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		//创建一个pojo
		ContentCategory contentCategory=new ContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
//		状态 1-正常，2-删除
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		
		contentCategoryMapper.insert(contentCategory);
//		查看父节点是否为true
		ContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
//		判断是否为true
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		
		return TaotaoResult.ok(contentCategory);
	}

}
