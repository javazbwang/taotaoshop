package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.ItemDesc;
import com.taotao.pojo.ItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

/**
 * 商品信息管理
 * 
 * @ClassName: ItemServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;

	/**
	 * 获取商品基础信息
	 * 
	 * @Function getItemById
	 * @author dwg
	 * @param itemId
	 * @return
	 * @see com.taotao.portal.service.ItemService#getItemById(java.lang.Long)
	 */
	@Override
	public ItemInfo getItemById(Long itemId) {

		try {
			// 调用rest的服务查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if (taotaoResult.getStatus() == 200) {
					ItemInfo item = (ItemInfo) taotaoResult.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取商品描述
	 * 
	 * @Function getItemDescById
	 * @author dwg
	 * @param itemId
	 * @return
	 * @see com.taotao.portal.service.ItemService#getItemDescById(java.lang.Long)
	 */
	@Override
	public String getItemDescById(Long itemId) {
		try {
			// 查询商品描述
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			// 转换成java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemDesc.class);
			if (taotaoResult.getStatus() == 200) {
				ItemDesc itemDesc = (ItemDesc) taotaoResult.getData();
				// 取商品描述信息
				String result = itemDesc.getItemDesc();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取商品规格
	 * @Function getItemParam
	 * @author dwg
	 * @param itemId
	 * @return 
	 * @see com.taotao.portal.service.ItemService#getItemParam(java.lang.Long)
	 */
	@Override
	public String getItemParam(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			// 把json转换成java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemParamItem.class);
			if (taotaoResult.getStatus() == 200) {
				ItemParamItem itemParamItem = (ItemParamItem) taotaoResult.getData();
				String paramData = itemParamItem.getParamData();
				// 生成html
				// 把规格参数json数据转换成java对象
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for (Map m1 : jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for (Map m2 : list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
						sb.append("            <td>" + m2.get("v") + "</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				// 返回html片段
				return sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}
