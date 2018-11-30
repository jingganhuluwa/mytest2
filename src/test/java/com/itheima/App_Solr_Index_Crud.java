package com.itheima;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class App_Solr_Index_Crud {
    private static HttpSolrServer solrServer;

    @BeforeClass
    public static void init(){
        solrServer = new HttpSolrServer("http://localhost:8080/solr");
    }
    @AfterClass
    public static void close() throws IOException, SolrServerException {
        solrServer.commit();
    }

    //添加索引
    @Test
    public void addOrUpdateIndex() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","1689");
        document.addField("name","you never know until you try");
        document.addField("content","a.jpj");
        solrServer.add(document);
    }


    //根据id删除索引
    @Test
    public  void deleteIndexById() throws IOException, SolrServerException {
        solrServer.deleteById("1689");
    }

    //根据条件删除索引
    @Test
    public void deleteIndexByQuery() throws IOException, SolrServerException {
        solrServer.deleteByQuery("name:never");
    }

    //查询索引
    @Test
    public void queryIndex() throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery("*:*");
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("搜索的结果总数:"+results.getNumFound());
        for (SolrDocument solrDocument : results) {
            System.out.println("-----华丽分割线-----");
            System.out.println("id域:"+solrDocument.get("id"));
            System.out.println("name域:"+solrDocument.get("name"));
        }
    }
}
