package com.taotao.httpclient;

import java.util.HashMap;

import org.junit.Test;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.SearchResult;

public class SearchTest {

	@Test
	public void search() {
		HashMap<String, String> param = new HashMap<>();
		param.put("keyword", "手机");
		param.put("page", 1 + "");
		param.put("rows", 10 + "");
		//调用服务
		String json = HttpClientUtil.doGet("http://localhost:8083/search/q", param);
		//转换成Java对象
		/*TaotaoResult r = JsonUtils.jsonToPojo(json, TaotaoResult.class);

		
		TaotaoResult r1 = TaotaoResult.formatToPojo(json, SearchResult.class);
		
		System.out.println(r.equals(r1));
		//System.out.println(r);
*/		
		
	}
}
