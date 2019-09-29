package com.taotao.rest.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	/**
	 * 添加
	 * @author dwg
	 * @throws Exception void
	 */
	@Test
	public void addDocument() throws Exception{
		//创建连接
		HttpSolrClient solrServer = new HttpSolrClient.Builder("http://192.168.10.40:8080/solr/collection1")
	            .withConnectionTimeout(10000)
	            .withSocketTimeout(60000)
	            .build();
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test003");
		document.addField("item_title", "测试商品3");
		document.addField("item_price", 654321);
		document.addField("item_sell_point", "哈哈哈哈哈哈哈 ");
		solrServer.add(document);
		//提交
        solrServer.commit();
	}
	/**
	 * 删除
	 * @author dwg
	 * @throws Exception void
	 */
	@Test
	public void delDocument() throws Exception{
		//创建连接
		HttpSolrClient solrServer = new HttpSolrClient.Builder("http://192.168.10.40:8080/solr/collection1")
				.withConnectionTimeout(10000)
				.withSocketTimeout(60000)
				.build();
//		solrServer.deleteById("test001");
		//根据查询删除
		solrServer.deleteByQuery("*:*");
        //提交
        solrServer.commit();
	}
	@Test
	public void query() throws SolrServerException, IOException {
		HttpSolrClient solrServer = new HttpSolrClient.Builder("http://192.168.10.40:8080/solr/collection1")
	            .withConnectionTimeout(10000)
	            .withSocketTimeout(60000)
	            .build();
	        SolrQuery query = new SolrQuery();
//	        query.set("q", "*:*");
	        query.setQuery("*:*");
	        query.setStart(20);
	        query.setRows(50);
	        // 调用server的查询方法，查询索引库
	        QueryResponse response = solrServer.query(query);
	        // 查询结果
	        SolrDocumentList results = response.getResults();
	        // 查询结果总数
	        long cnt = results.getNumFound();
	        System.out.println("查询结果总数:" + cnt);
	        for (SolrDocument solrDocument : results) {
	            System.out.println(solrDocument.get("id"));
	            System.out.println(solrDocument.get("item_title"));
	            System.out.println(solrDocument.get("item_price"));
	        }
	}
}
