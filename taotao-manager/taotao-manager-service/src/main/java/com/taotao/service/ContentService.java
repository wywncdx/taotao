package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	TaotaoResult addContent(TbContent content);
	TaotaoResult deleteContents(String ids);
	TbContent getContentById(Long id);
	EUDateGridResult getContentsByCatId(Long catId, int pageIndex, int rows);
}
