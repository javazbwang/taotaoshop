package com.taotao.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.web.util.UriBuilder;

public class HttpClientTest {
	@Test
	public void doGet() throws Exception{
		//创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		创建get对象
		HttpGet get=new HttpGet("http://www.baidu.com");
//		执行请求
		CloseableHttpResponse response = httpClient.execute(get);
//		获取响应结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity,"utf-8");
		System.out.println(string);
//		关闭httpclient
		response.close();
		httpClient.close();
	}
	@Test
	public void doGetWithParam() throws Exception{
//		创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		创建URI对象
		URIBuilder uriBuilder=new URIBuilder("http://www.sogou.com/sie");
		uriBuilder.addParameter("query", "天天向上");
		HttpGet get=new HttpGet(uriBuilder.build());
//		执行请求
		CloseableHttpResponse response = httpClient.execute(get);
//		获取响应结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity,"utf-8");
		System.out.println(string);
//		关闭httpclient
		response.close();
		httpClient.close();
	}
	
	@Test
	public void doPost() throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		创建Post对象
		
		HttpPost post=new HttpPost("http://localhost:8082/httpclient/post.html");
//		执行请求
		CloseableHttpResponse response = httpClient.execute(post);
		String string = EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println(string);
		response.close();
		httpClient.close();
	}
	@Test
	public void doPostWithParam() throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		创建Post对象
		HttpPost post=new HttpPost("http://localhost:8082/httpclient/post.html");
//		创建一个entity，模拟表单
		List<NameValuePair> kvList=new ArrayList<NameValuePair>();
		kvList.add(new BasicNameValuePair("name", "zhang"));
		kvList.add(new BasicNameValuePair("password", "123456"));
//		包装成一个Entity对象
		StringEntity entity=new UrlEncodedFormEntity(kvList,"utf-8");
//		设置请求内容
		post.setEntity(entity);
//		执行请求
		CloseableHttpResponse response = httpClient.execute(post);
		String string = EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println(string);
		response.close();
		httpClient.close();
	}
}
