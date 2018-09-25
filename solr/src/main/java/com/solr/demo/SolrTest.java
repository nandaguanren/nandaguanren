package com.solr.demo;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-solr.xml")
public class SolrTest {
	
	@Autowired
	private SolrServer server;
	/**
	 * 简单查询
	 * @throws Exception
	 */
	@Test
	public void search() throws Exception{
		
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse response = server.query(query);
		
		SolrDocumentList results = response.getResults();
		
		for (SolrDocument solrDocument : results) {
			
			System.out.println(solrDocument.get("item_title"));
		}
		
		System.out.println("总记录数:"+results.getNumFound());
		
	}
	/**
	 * 添加
	 * @throws Exception
	 */
	@Test
	public void add() throws Exception{
		//创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域,文档中必须包含一个id域,所有的域名称必须在schema.xml中定义
		document.addField("id", 1L);
		document.addField("item_title", "南大官人");
		document.addField("item_price", 123);
		document.addField("item_spec_name", "南");
		//把文档写入索引库
		server.add(document);
		//提交
		server.commit();
	}
	
	/**
	 * 删除文档对象
	 */
	@Test
	public void deleteDoc() throws Exception{
		//按照id删除文档
		server.deleteById("1");
		
	//	server.deleteByQuery("*:*"); 查询删除
		server.commit();
	}
	/**
	 * 更新索引库,没有特定的api,直接添加,覆盖即可,或者先删除再添加
	 * 
	 * 
	 */
}
