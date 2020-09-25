package com.paul.webcrawler.service;

import java.io.File;

import org.springframework.stereotype.Service;

import com.paul.webcrawler.model.WebUrlRequest;
import com.paul.webcrawler.model.WebUrlResponse;
import com.paul.webcrawler.util.HtmlCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Service
public class WebCrawlerService {
	public WebUrlResponse crawlWebPages(WebUrlRequest webUrlRequest) throws Exception {
		
		WebUrlResponse webUrlResponse = new WebUrlResponse();
		
		
		File crawlStorage = new File("src/test/resources/crawler4j");
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
		 
		int numCrawlers = 12;
		 
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer= new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		 
		controller.addSeed("https://www.wipro.com/");
		 
		CrawlController.WebCrawlerFactory<HtmlCrawler> factory = HtmlCrawler::new;
		 
		controller.start(factory, numCrawlers);		
		
		return webUrlResponse;
	}
	
}
