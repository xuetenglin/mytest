package com.itheima.test;
/**
 * solr的复杂查询
 * @author Administrator
 *
 */

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrManager {
	
	/**
	 * 查询操作
	 * @throws Exception 
	 */
	@Test
	public void testquery() throws Exception {
		SolrServer solrserver=new HttpSolrServer("http://localhost:8080/solr/collection1");
		SolrQuery solrquery=new SolrQuery();
		
//		solrquery.setQuery("name:solr6");//两种查询方式都可以
		solrquery.set("q", "花儿");//有默认查询域name:solr   所以就可以将name省略不写
//		设置默认查询的域名
		solrquery.set("df", "product_keywords");
//		设置过滤的条件
//		solrquery.set("product_catalog_name:美味厨房");  两种写法都可以
		solrquery.addFilterQuery("product_catalog_name:美味厨房");
		solrquery.addFilterQuery("product_price:[* TO 10]");//		设置价格查询的价格区间
		
//		设置每页展示信息的条数
		solrquery.setStart(0);//起始索引
		solrquery.setRows(10);//结束索引
		
//		设置价格的排序
		solrquery.setSort("product_price", ORDER.asc);
		
//		开启高亮显示
		solrquery.setHighlight(true);
		
//		设置高亮的样式以及高亮显示的filed域
		solrquery.setHighlightSimplePre("<span style=\"color:red\">");
		solrquery.setHighlightSimplePost("</span>");
		solrquery.addHighlightField("product_name");
		
		QueryResponse qr=solrserver.query(solrquery);
		
		Map<String, Map<String, List<String>>> highlighting = qr.getHighlighting();
		
		SolrDocumentList list=qr.getResults();
		
		System.out.println("总条数："+list.getNumFound());
	
		for (SolrDocument solrDocument : list) {
			Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
			
			List<String> list2 = map.get("product_name");
			
			String product_name="";
			
			if (list2!=null && list2.size()>0) {
				product_name=list2.get(0);
			}else {
				product_name=(String) solrDocument.get("product_name");
			}
			
			
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_picture"));
			System.out.println(solrDocument.get("product_catalog_name"));
			
		}
	
	}
	
}
