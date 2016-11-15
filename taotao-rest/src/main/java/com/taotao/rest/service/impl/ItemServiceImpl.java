package com.taotao.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	
	@Value("${ITEM_BASE_INFO_KEY}")
	private String ITEM_BASE_INFO_KEY;
	
	@Value("${ITEM_EXPIRE_SECONDS}")
	private Integer ITEM_EXPIRE_SECONDS;
	
	@Override
	public TbItem getItemById(Long itemId) {
		//查询缓存，如果有缓存，直接返回
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + "ITEM_BASE_INFO_KEY" + ":" + itemId);
			//判断数据是否存在
			if(StringUtils.isNotBlank(json)) {
				//把json转换成java对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id，查询数据库
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		//向redis中添加缓存，商品信息最好要设置过期时间，
		//因为如果用户访问了一个非常冷门的商品，不设置过期时间的话，该商品信息则会一直保存在redis中，浪费资源。
		//过期时间只能在key上设置，不能在hash的field(项)上设置。
		//添加缓存原则，不能影响正常业务逻辑。
		try {
			//向redis中添加缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + "ITEM_BASE_INFO_KEY" + ":" + itemId, 
					JsonUtils.objectToJson(item));
			//设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + "ITEM_BASE_INFO_KEY" + ":" + itemId, 
					ITEM_EXPIRE_SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return item;
	}
}
