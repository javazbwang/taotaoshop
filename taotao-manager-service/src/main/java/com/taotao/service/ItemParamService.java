package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.ItemParam;

public interface ItemParamService {

	TaotaoResult getItemParamByCid(long cid);

	TaotaoResult insertItemParam(ItemParam itemParam);

}
