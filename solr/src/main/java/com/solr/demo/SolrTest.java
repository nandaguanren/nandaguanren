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
	 * �򵥲�ѯ
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
		
		System.out.println("�ܼ�¼��:"+results.getNumFound());
		
	}
	/**
	 * ���
	 * @throws Exception
	 */
	@Test
	public void add() throws Exception{
		//�����ĵ�����
		SolrInputDocument document = new SolrInputDocument();
		//���ĵ������������,�ĵ��б������һ��id��,���е������Ʊ�����schema.xml�ж���
		document.addField("id", 1L);
		document.addField("item_title", "�ϴ����");
		document.addField("item_price", 123);
		document.addField("item_spec_name", "��");
		//���ĵ�д��������
		server.add(document);
		//�ύ
		server.commit();
	}
	
	/**
	 * ɾ���ĵ�����
	 */
	@Test
	public void deleteDoc() throws Exception{
		//����idɾ���ĵ�
		server.deleteById("1");
		
	//	server.deleteByQuery("*:*"); ��ѯɾ��
		server.commit();
	}
	/**
	 * ����������,û���ض���api,ֱ�����,���Ǽ���,������ɾ�������
	 * 
	 * 
	 */
}
