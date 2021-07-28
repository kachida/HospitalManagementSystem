package com.elasticsearchsvc.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {
	
	private final RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("elasticsearch",9200,"http"), new HttpHost("elasticsearch",9300,"http")));
	
	
	public Map<String, Object> executeElasticSearchQuery(String query) throws IOException {
		
		StringBuilder sb = new StringBuilder(query);
		Map<String, Object> sourceAsMap = new HashMap<String,Object>();
	    SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(sb.toString()));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits)
        {
        	sourceAsMap = hit.getSourceAsMap();
        }
		return sourceAsMap;
	}

}
