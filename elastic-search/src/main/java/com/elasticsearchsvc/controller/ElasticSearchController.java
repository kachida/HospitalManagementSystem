package com.elasticsearchsvc.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearchsvc.service.ElasticSearchService;

@RestController("/elasticsearch")
public class ElasticSearchController {
	
	private ElasticSearchService elasticService;
	
	public ElasticSearchController(ElasticSearchService elasticService) {
		super();
		this.elasticService = elasticService;
	}



	@GetMapping("/")
	public Map<String,Object> executeElasticSearchQuery(@RequestParam(name = "q") String query) throws IOException
	{
		return elasticService.executeElasticSearchQuery(query);
	}

}
