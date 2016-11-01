package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer; 
	
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		List<SearchItem> itemList = new ArrayList<SearchItem>();
		for (SolrDocument doc : results) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) doc.get("item_category_name"));
			item.setId((String) doc.get("id"));
			item.setImage((String) doc.get("item_image"));
			item.setPrice((Long) doc.get("item_price"));
			item.setSell_point((String) doc.get("item_sell_point"));
			
			//取高亮显示，需要好好理解solr高亮的特性。
			String itemTitle = null;
			Map<String, Map<String, List<String>>> highlightingList = response.getHighlighting();
			List<String> HLItemTitleList = highlightingList.get(doc.get("id")).get("item_title");
			if (HLItemTitleList != null && HLItemTitleList.size() > 0 ) {
				itemTitle = HLItemTitleList.get(0);
			} else {
				itemTitle = (String) doc.get("item_title");
			}
			item.setTitle(itemTitle);
			itemList.add(item);
		}
		SearchResult searchResult = new SearchResult();
		searchResult.setItemList(itemList);
		searchResult.setRecordCount(results.getNumFound());
		return searchResult;
	}
}
