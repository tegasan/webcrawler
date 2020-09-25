package com.paul.webcrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.paul.webcrawler.model.WebUrlRequest;
import com.paul.webcrawler.model.WebUrlResponse;
import com.paul.webcrawler.service.WebCrawlerService;


@Controller
public class WebCrawlerController {

	@Autowired
	WebCrawlerService webCrawlerService;
	
    @GetMapping("/pages")
    public String startForm(WebUrlRequest webUrlRequest) {
    		
    		System.out.println("test me");
        return "start";
    }
    
	@PostMapping("/pages")
	public String createPages(WebUrlRequest webUrlRequest) throws Exception {
		WebUrlResponse webUrlResponse = new WebUrlResponse();
		webUrlResponse = webCrawlerService.crawlWebPages(webUrlRequest);
		System.out.println("url: " + webUrlRequest.getStartUrl());
		System.out.println("depth: " + webUrlRequest.getDepthCrawl());
		
		return "response";
		
	}
	
}
