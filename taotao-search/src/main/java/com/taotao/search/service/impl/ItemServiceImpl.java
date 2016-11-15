package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.service.ItemService;

/**
 * 商品导入service
 * 
 * @author wyw
 *
 */

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private SolrServer solrServer;

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public TaotaoResult importItems() throws Exception {
		List<SearchItem> itemList = itemMapper.getItemList();
		for (SearchItem searchItem : itemList) {
			// 创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			// 添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());
			// 写入索引库
			solrServer.add(document);
		}
		solrServer.commit();
		return TaotaoResult.ok();
	}

}
