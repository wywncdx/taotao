package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public TaotaoResult getItemParemByCid(@PathVariable("cid") Long cid) {
		TbItemParam itemParam = itemParamService.getItemParamByCid(cid);
		return TaotaoResult.ok(itemParam);
	}
	
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult addItemParam(@PathVariable("cid") Long cid, String paramData) {
		TaotaoResult result = itemParamService.addItemParam(cid, paramData);
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDateGridResult getItemParamByPage(int page, int rows) {
		EUDateGridResult itemParamPage = itemParamService.getItemParamByPage(page, rows);
		return itemParamPage;
	}
}
