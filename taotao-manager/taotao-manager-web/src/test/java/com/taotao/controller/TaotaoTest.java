package com.taotao.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.PictureService;
import com.taotao.service.impl.PictureServiceImpl;


public class TaotaoTest {

	@Test
	public void TestPageHelper() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		TbItemMapper itemMapper = ctx.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(1, 10);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		for(TbItem item : itemList){
			System.out.println(item.getTitle());
		}
		PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
		long total = pageInfo.getTotal();
		System.out.println(">>>>>>>>>>>>>>." + total);
		
	}
	
	@Test
	public void TestPlaceholder() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		PictureService bean = ctx.getBean(PictureService.class);
		
		//System.out.println(">>>>" + bean.getIMAGE_SERVER_BASE_URL());
		
		
	}
	
}
