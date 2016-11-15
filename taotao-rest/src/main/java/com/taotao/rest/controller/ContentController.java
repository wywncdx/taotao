package com.taotao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;

/*
 rest的controller层是发布rest服务用的
 */
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/{cid}")
	@ResponseBody
	public TaotaoResult getContentListByCatId(@PathVariable Long cid) {
		/*
		 * rest项目作为portal项目调用的服务，那么假如出错了，需要把错误信反馈给portal，所以需要try/catch处理下。
		 */
		try {
			List<TbContent> contentList = contentService.getContentsByCatId(cid);
			return TaotaoResult.ok(contentList);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	/**
	 * 当修改了数据库的内容后，redis中缓存的还是修改前的数据。
	 * 同步即删除redis中缓存的相关的数据
	 * @param cid
	 * @return
	 */
	@RequestMapping("/sync/content/{cid}") 
	@ResponseBody
	public TaotaoResult syncContent(@PathVariable Long cid) {
		try {
			TaotaoResult result = contentService.syncContent(cid);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
