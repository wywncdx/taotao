package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper; 
	
	@Override
	public TbItemParam getItemParamByCid(Long cid) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(cid);
		List<TbItemParam> itemParmList = itemParamMapper.selectByExampleWithBLOBs(example);
		if(itemParmList != null && itemParmList.size() > 0) {
			return itemParmList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public TaotaoResult addItemParam(Long cid, String paramData) {
		TbItemParam itemPara = new TbItemParam();
		itemPara.setItemCatId(cid);
		itemPara.setParamData(paramData);
		Date date = new Date();
		itemPara.setCreated(date);
		itemPara.setUpdated(date);
		itemParamMapper.insert(itemPara);
		return TaotaoResult.ok();
	}

	@Override
	public EUDateGridResult getItemParamByPage(int page, int rows) {
		TbItemParamExample example = new TbItemParamExample();
		PageHelper.startPage(page, rows);
		List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbItemParam> itemParamPageInfo = new PageInfo<>(itemParamList);
		EUDateGridResult pageResult = new EUDateGridResult();
		pageResult.setRows(itemParamList);
		pageResult.setTotal(itemParamPageInfo.getTotal());
		return pageResult;
	}
	
	
	
}
