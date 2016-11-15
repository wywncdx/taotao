package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	
	@Override
	public List<TbContent> getContentsByCatId(Long contentCatId) {
		//添加缓存
		//查询数据库之前先查询缓存，如果有缓存，直接返回缓存。
		try {
			//从redis中取缓存数据
			String contentListJson = jedisClient.hget(REDIS_CONTENT_KEY, contentCatId+"");
			if(!StringUtils.isBlank(contentListJson)) {
				//把json转换为List
				List<TbContent> contentList = JsonUtils.jsonToList(contentListJson, TbContent.class);
				return contentList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//如果没有缓存，则根据cid查询数据库
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(contentCatId);
		List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);
		//返回结果之前，先向缓存中添加数据
		try {
			//使用redis的hash数据类型
			//定义一个保存内容的key，cid作为field，contentList转换为json的字符串作为value。
			jedisClient.hset(REDIS_CONTENT_KEY, contentCatId+"", JsonUtils.objectToJson(contentList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentList;
	}


	@Override
	public TaotaoResult syncContent(Long cid) {
		jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		return TaotaoResult.ok();
	}
}
