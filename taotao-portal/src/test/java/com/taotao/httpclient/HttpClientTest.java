package com.taotao.httpclient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.taotao.common.utils.HttpClientUtil;

public class HttpClientTest {
	
	@Test
	public void httpGet() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://www.sina.com");
		CloseableHttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println(">>>" + result);
		response.close();
		httpClient.close();
	}
	
	
	@Test
	public void httpGet1() {
		String result = HttpClientUtil.doGet("http://localhost:8081/rest/sync/content/89");
		//System.out.println(result);
		
		
	}
	@Test
	public void httpPost() throws Exception {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://localhost:8082/posttest.html");
		//准备一个form表单数据，封装到StringEntity中。
		List<NameValuePair> formList = new ArrayList<>();
		formList.add(new BasicNameValuePair("name", "张三"));
		formList.add(new BasicNameValuePair("pass", "123123"));
		StringEntity entity = new UrlEncodedFormEntity(formList, "utf-8");
		//把entity设置到post中
		post.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(post);
		HttpEntity responsEntity = response.getEntity();
		String result = EntityUtils.toString(responsEntity);
		System.out.println(result);
		response.close();
		httpClient.close();
	}
}
