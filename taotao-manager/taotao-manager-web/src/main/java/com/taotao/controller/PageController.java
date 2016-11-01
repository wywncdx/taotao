package com.taotao.controller;

import org.aspectj.weaver.Shadow;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 * @author wyw
 *
 */
@Controller
public class PageController {

	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping("/{page}")
	public String showPage(@PathVariable(value="page") String page) {
		return page;
	}
}
