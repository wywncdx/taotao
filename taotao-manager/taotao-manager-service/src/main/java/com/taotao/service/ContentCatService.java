package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCatService {
	List<EUTreeNode> getContentCatList(Long parentId);
	TaotaoResult addContentCat(Long parentId, String name);
}
