package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.service.ContentService;

/**
 * 
 * @author wyw
 *
 */
@Controller
public class IndexCotroller {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		//取大广告位内容（轮播图）
		String adJson = contentService.getAD1List();
		//传递给页面
		model.addAttribute("ad1", adJson);
		return "index";
	}
	
	
	//httpclient的测试方法
	@RequestMapping(value="/posttest", method=RequestMethod.POST)
	@ResponseBody
	public String postTest(String name, String pass) {
		System.out.println(name);
		System.out.println(pass);
		return "OK";
	}
}
