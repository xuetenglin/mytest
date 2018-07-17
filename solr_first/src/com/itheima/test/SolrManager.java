package com.itheima.test;
/**
 * solr的简单实用：增删改查
 * @author Administrator
 *
 */

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrManager {
	/**
	 * 添加
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
//
	@Test
	public void testadd() throws Exception {
		SolrServer solrserver=new HttpSolrServer("http://localhost:8080/solr/collection1");
		for (int i = 0; i < 11; i++) {
			
		SolrInputDocument doc=new SolrInputDocument();
		
		doc.addField("id",i);
		doc.addField("name", "solr"+i);
		
		solrserver.add(doc);
		}
		solrserver.commit();
	
	}
	
	/**
	 * 修改操作
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
	@Test
	public void testupdate() throws Exception {
		SolrServer solrserver=new HttpSolrServer("http://localhost:8080/solr/collection1");
//		在id一样的情况下，进行修改操作就会对原数据直接进行覆盖
		SolrInputDocument doc=new SolrInputDocument();
		doc.addField("id", "2");
		doc.addField("name", "dsfdsfsdfsdf");
		solrserver.add(doc);
		solrserver.commit();
	}
	
	/**
	 * 删除操作
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
	
	@Test
	public void testdelete() throws Exception {
		SolrServer solrserver=new HttpSolrServer("http://localhost:8080/solr/collection1");
//		直接进行删除操作
//		solrserver.deleteById("1");
		solrserver.deleteByQuery("name:dsfdsfsdfsdf");
		
		solrserver.commit();
		
	}
	
	
	/**
	 * 查询操作
	 * @throws Exception 
	 */
	@Test
	public void testquery() throws Exception {
		SolrServer solrserver=new HttpSolrServer("http://localhost:8080/solr/collection1");
		SolrQuery solrquery=new SolrQuery();
		
		solrquery.setQuery("name:solr6");
//		solrquery.set("q", "solr");//有默认查询域name:solr   所以就可以将name省略不写
		
		QueryResponse qr=solrserver.query(solrquery);
		SolrDocumentList list=qr.getResults();
		
		System.out.println("总条数："+list.getNumFound());
	
		for (SolrDocument solrDocument : list) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("name"));
			
		}
	
	
	}
	
	
	
}
