package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public TaotaoResult addContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}

	@Override
	public EUDateGridResult getContentsByCatId(Long catId, int pageIndex, int rows) {
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(catId);
		PageHelper.startPage(pageIndex, rows);
		List<TbContent> contentList = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contentList);
		long total = pageInfo.getTotal();
		EUDateGridResult result = new EUDateGridResult();
		result.setRows(contentList);
		result.setTotal(total);
		return result;
	}

	@Override
	public TaotaoResult deleteContents(String ids) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			contentMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TbContent getContentById(Long id) {
		TbContent content = contentMapper.selectByPrimaryKey(id);
		return content;
	}

}
