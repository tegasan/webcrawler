package com.paul.webcrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.paul.webcrawler.model.WebUrlRequest;
//import com.paul.webcrawler.model.WebUrlResponse;

@Controller
public class CrawlerController {

    @GetMapping("/pages")
    public String startForm(WebUrlRequest webUrlRequest) {

    		System.out.println("test me");
        return "start";
    }
    
	@PostMapping("/pages")
	public String createPages(WebUrlRequest webUrlRequest) {
		
		
		
		return "response";
		
	}
	
}
