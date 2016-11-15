package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${REST_CONTENT_AD1_CID}")
	private String REST_CONTENT_AD1_CID;
	
	
	@Override
	public String getAD1List() {
		//调用rest服务获取数据
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
		//把json转换为java对象
		TaotaoResult taotaoResult= TaotaoResult.formatToList(json, TbContent.class);
		//取data属性，内容列表
		List<TbContent> contentList = (List<TbContent>)taotaoResult.getData();
		//把内容列表，转换为AdNode列表
		ArrayList<AdNode> adNodeList = new ArrayList<AdNode>();
		for (TbContent tbContent : contentList) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(tbContent.getPic());
			
			node.setHeight(240);
			node.setWidth(550);
			node.setSrc(tbContent.getPic2());
			
			node.setAlt(tbContent.getSubTitle());
			node.setHref(tbContent.getUrl());
			
			adNodeList.add(node);
		}
		//把adNodeList转换为json数据
		String adNodeListJson = JsonUtils.objectToJson(adNodeList);
		return adNodeListJson;
	}
}
