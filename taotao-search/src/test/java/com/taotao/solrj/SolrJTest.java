package com.taotao.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	@Test
	public void testSolrCloud() throws Exception {
		//创建一个solrServer对象
		CloudSolrServer solrServer = new CloudSolrServer(
				"192.168.25.154:2181,192.168.25.154:2182,192.168.25.154:2183"
				);
		//设置默认的collection
		solrServer.setDefaultCollection("collection2");
		//创建文档对象
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "test01");
		doc.addField("item_title", "title01");
		//添加文档
		solrServer.add(doc);
		//提交
		solrServer.commit();
	}
}
