package com.paul.webcrawler.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.paul.webcrawler.entity.PagesEntity;
import com.paul.webcrawler.model.WebUrlRequest;
import com.paul.webcrawler.model.WebUrlResponse;
import com.paul.webcrawler.service.WebCrawlerService;


@Controller
public class WebCrawlerController {

	@Autowired
	WebCrawlerService webCrawlerService;
	
    @GetMapping("/pages")
    public String startForm(WebUrlRequest webUrlRequest) {
    	webUrlRequest.setDepthCrawl("1");
    	webUrlRequest.setStartUrl("https://www.wipro.com");
        return "start";
    }
    
	@PostMapping("/pages")
	public String createPages(WebUrlRequest webUrlRequest) throws Exception {
		webCrawlerService.crawlWebPages(webUrlRequest);
		System.out.println("End of Web Crawling");
		return "redirect:pages/result";
	}
	
    @GetMapping("/pages/result")
    public String getResult(Model model) {
    	List<PagesEntity> listPagesEntity = new ArrayList<PagesEntity>();
    	listPagesEntity = webCrawlerService.getResult();
    	
    	model.addAttribute("pages", listPagesEntity);
        return "response";
    }
}
