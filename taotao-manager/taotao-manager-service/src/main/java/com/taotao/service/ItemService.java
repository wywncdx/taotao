package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem geTbItemById(long itemId);
	EUDateGridResult getItemListByPage(int page, int rows);
	TaotaoResult addItem(TbItem item, String desc, String itemParams);
}
