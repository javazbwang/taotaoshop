package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.ItemParamMapper;
import com.taotao.pojo.ItemParam;
import com.taotao.pojo.ItemParamExample;
import com.taotao.pojo.ItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
/**
 * 商品规格
 * @ClassName: ItemParamServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private ItemParamMapper itemParamMapper;
	
	/**
	 * 获取param
	 * @Function getItemParamByCid
	 * @author dwg
	 * @param cid
	 * @return 
	 * @see com.taotao.service.ItemParamService#getItemParamByCid(long)
	 */
	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		ItemParamExample example = new ItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<ItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		
		return TaotaoResult.ok();
	}
	/**
	 * 添加param
	 * @Function insertItemParam
	 * @author dwg
	 * @param itemParam
	 * @return 
	 * @see com.taotao.service.ItemParamService#insertItemParam(com.taotao.pojo.ItemParam)
	 */
	@Override
	public TaotaoResult insertItemParam(ItemParam itemParam) {
		//补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入到规格参数模板表
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}


}
