package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 
 * @author wyw
 *
 */

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem geTbItemById(long itemId) {
//		itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		if(itemList != null && itemList.size() > 0) {
			return itemList.get(0);
		}
		return null;
	}

	@Override
	public EUDateGridResult getItemListByPage(int page, int rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
		
		EUDateGridResult result = new EUDateGridResult();
		result.setRows(itemList);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	

	@Override
	public TaotaoResult addItem(TbItem item, String desc, String itemParams) {
		//注意，如果代码中使用try/catch包裹了代码，那么一定要在catch中再把异常跑出去，否则，事务失效。
		long itemId = IDUtils.genItemId();
		//插入商品表
		item.setId(itemId);
		item.setStatus((byte)1); //'1' COMMENT '商品状态，1-正常，2-下架，3-删除'
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);
		
		//插入商品描述表
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDescMapper.insert(itemDesc);
		
		//插入商品规格参数表
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}

}
