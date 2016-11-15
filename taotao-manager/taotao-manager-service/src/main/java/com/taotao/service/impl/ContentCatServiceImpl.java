package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCatService;

@Service
public class ContentCatServiceImpl implements ContentCatService {
	
	@Autowired
	private TbContentCategoryMapper contentCatMapper;
	
	@Override
	public List<EUTreeNode> getContentCatList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCatMapper.selectByExample(example);
		ArrayList<EUTreeNode> nodeList = new ArrayList<EUTreeNode>();
		for (TbContentCategory cat : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(cat.getId());
			node.setState(cat.getIsParent()?"closed":"open");
			node.setText(cat.getName());
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public TaotaoResult addContentCat(Long parentId, String name) {
		TbContentCategory cat = new TbContentCategory();
		cat.setName(name);
		cat.setParentId(parentId);
		//状态。可选值:1(正常),2(删除)
		cat.setStatus(1);
		cat.setIsParent(false);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		cat.setSortOrder(1);
		Date date = new Date();
		cat.setCreated(date);
		cat.setUpdated(date);
		//插入数据
		contentCatMapper.insert(cat);
		//取返回的主键，这个需要在mapper.xml中配置
		Long id = cat.getId();
		//判断父节点的isParent属性
		TbContentCategory parentNode = contentCatMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			contentCatMapper.updateByPrimaryKey(parentNode);
		}
		return TaotaoResult.ok(id);
	}

}
