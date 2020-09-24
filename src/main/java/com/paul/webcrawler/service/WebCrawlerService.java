package com.paul.webcrawler.service;

import org.springframework.stereotype.Service;

import com.paul.webcrawler.model.WebUrlRequest;
import com.paul.webcrawler.model.WebUrlResponse;

@Service
public class WebCrawlerService {
	WebUrlResponse generateWebCrawler(WebUrlRequest webUrlRequest) {
		
		WebUrlResponse webUrlResponse = new WebUrlResponse();
		
		return webUrlResponse;
	}
	
}
