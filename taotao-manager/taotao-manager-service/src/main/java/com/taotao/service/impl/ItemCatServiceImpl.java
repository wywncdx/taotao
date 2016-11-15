package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public List<EUTreeNode> getItemCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		ArrayList<EUTreeNode> resultList = new  ArrayList<EUTreeNode>();
		for(TbItemCat item : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(item.getId());
			node.setText(item.getName());
			node.setState(item.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		return resultList;
	}

}
