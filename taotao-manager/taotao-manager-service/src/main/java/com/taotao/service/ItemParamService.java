package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
	
	TbItemParam getItemParamByCid(Long cid);
	TaotaoResult addItemParam(Long cid, String paramData);
	EUDateGridResult getItemParamByPage(int page, int rows);
}
