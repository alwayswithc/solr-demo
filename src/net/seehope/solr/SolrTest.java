package net.seehope.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	//保存与更新
	
	public void testSaveOrUpdateDocument() throws SolrServerException, IOException {
		String url="http://localhost:8080/solr/taobao_item";
		HttpSolrClient client=new HttpSolrClient.Builder(url).
				withConnectionTimeout(500).withSocketTimeout(500).build();
		
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "11111");
		document.addField("product_name", "hello solr 123654");
		document.addField("product_catalog", "solr 从入门到放弃");
		
		client.add(document);
		client.commit();
		client.close();
	}
	/**
	 * startIndex 设置分页
	 * query.setStart(null);
	 * query.setRows(null);
	 * 
	 *	设置高亮
	 *	query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<span style='color:red'>");
		query.setHighlightSimplePost("</span>");
		
		
	 * @throws SolrServerException
	 * @throws IOException
	 */
	
	//查询
	@Test
	public void testQueryDocument() throws SolrServerException, IOException {
		String url="http://localhost:8080/solr/taobao_item";
		HttpSolrClient client=new HttpSolrClient.Builder(url).
				withConnectionTimeout(500).withSocketTimeout(500).build();
		//条件
		SolrQuery query=new SolrQuery();
		query.setQuery("product_name:solr product_name:123");
		
		QueryResponse queryResponse=client.query(query);
		SolrDocumentList docs=queryResponse.getResults();
		System.out.println("击中了"+docs.getNumFound()+"条记录");
		for (SolrDocument solrDocument : docs) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_catalog"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_description"));
			System.out.println(solrDocument.get("product_picture"));
		}
	}
}
