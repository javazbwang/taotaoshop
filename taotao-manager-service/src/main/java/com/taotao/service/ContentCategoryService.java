package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.TreeNode;

public interface ContentCategoryService {
	List<TreeNode> getCategoryList(long parentId);
}
