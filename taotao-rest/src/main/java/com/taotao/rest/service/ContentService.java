package com.taotao.rest.service;

import java.util.List;

import com.taotao.pojo.Content;

public interface ContentService {
	List<Content> getContentList(long contentId);
}
