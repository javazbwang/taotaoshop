package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.ItemCatMapper;
import com.taotao.pojo.ItemCat;
import com.taotao.pojo.ItemCatExample;
import com.taotao.pojo.ItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
/**
 * 商品分类
 * @ClassName: ItemCatServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	ItemCatMapper itemCatMapper;
	/**
	 * 获取分类
	 * @Function getItemCatList
	 * @author dwg
	 * @return 
	 * @see com.taotao.rest.service.ItemCatService#getItemCatList()
	 */
	@Override
	public CatResult getItemCatList() {
		CatResult catResult=new CatResult();
//		查询分类列表
		catResult.setData(getCatList(0));
		return catResult;
	}
	/**
	 * 创建分类列表
	 * @author dwg
	 * @param i
	 * @return List<?>
	 */
	private List<?> getCatList(long ParentId) {
		//分类查询条件 
		ItemCatExample example=new ItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(ParentId);
		//执行
		List<ItemCat> list = itemCatMapper.selectByExample(example);
		//返回值list
		List resultList=new ArrayList<>();
		//向list中添加节点
		int count=0;
		for (ItemCat itemCat:list) {
			//如果判断是父节点
			if (itemCat.getIsParent()) {
				CatNode catNode=new CatNode();
				if(ParentId==0) {
					catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
				}else {
					catNode.setName(itemCat.getName());
				}
				catNode.setUrl("/products/"+itemCat.getId()+".html");
				catNode.setItem(getCatList(itemCat.getId()));
				
				resultList.add(catNode);
				count++;
				if(count>=14&&ParentId==0) {
					break;
				}
			//如果是叶子节点
			}else {
				resultList.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
			}
		}
			
		return resultList;
	}

}
