package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCatService;

@Service
@RequestMapping("/content/category")
public class ContentCatController {

	@Autowired
	private ContentCatService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0") Long parentId) {
		List<EUTreeNode> resultList = contentCategoryService.getContentCatList(parentId);
		return resultList;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult addContentCat(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.addContentCat(parentId, name);
		return result;
	}
}
