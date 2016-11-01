package com.taotao.controller;

import java.awt.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 
 * @author wyw
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem geItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.geTbItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDateGridResult getItemsByPage(Integer page, Integer rows) {
		return itemService.getItemListByPage(page, rows);
	}
	
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc, String itemParams) {
		TaotaoResult result = itemService.addItem(item, desc, itemParams);
		return result;
	}
}

