package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.ItemCatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public ItemCatResult getItemCatList() {
		List resultList = getItemCatList(0L);
		ItemCatResult result = new ItemCatResult();
		result.setData(resultList);
		return result;
	}

	private List getItemCatList(Long parentId) {
		ArrayList resultList = new ArrayList();
		TbItemCatExample example = new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		int count = 0;
		for (TbItemCat itemCat : list) {
			if(count >= 14) {
				break;
			}
			// 如果是父节点
			if (itemCat.getIsParent()) {
				ItemCatNode node = new ItemCatNode();
				String url = "/products/" + itemCat.getId() + ".html";
				node.setUrl(url);
				// 如果是第一级父节点
				if (itemCat.getParentId() == 0) {
					node.setName("<a href='" + url + "'>" + itemCat.getName() + "</a>");
					count++;
				} else {
					// 如果不是第一级父节点
					node.setName(itemCat.getName());
				}
				node.setItems(getItemCatList(itemCat.getId()));
				resultList.add(node);
			} else {
				// 如果是叶子节点
				String cat = "/products/" + itemCat.getId() + ".html|" + itemCat.getName();
				resultList.add(cat);
			}
		}
		return resultList;
	}
}
