package com.taotao.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TreeNode;
import com.taotao.mapper.ItemCatMapper;
import com.taotao.pojo.ItemCat;
import com.taotao.pojo.ItemCatExample;
import com.taotao.pojo.ItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
/**
 * 商品分类
 * @ClassName: ItemCatServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Override
	public List<TreeNode> getCatList(Long parentId) {
		ItemCatExample example=new ItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<ItemCat> list = itemCatMapper.selectByExample(example);
		List<TreeNode> resultList=new ArrayList<TreeNode>();
		for (ItemCat itemCat : list) {
			TreeNode treeNode=new TreeNode();
			treeNode.setId(itemCat.getId());
			treeNode.setText(itemCat.getName());;
			treeNode.setState(itemCat.getIsParent()?"closed":"open");
			resultList.add(treeNode);
		}
		return resultList;
	}

}
