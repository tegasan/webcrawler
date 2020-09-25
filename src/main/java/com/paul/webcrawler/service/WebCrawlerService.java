package com.paul.webcrawler.service;

import java.io.File;

import org.springframework.stereotype.Service;

import com.paul.webcrawler.model.WebUrlRequest;
import com.paul.webcrawler.model.WebUrlResponse;
import com.paul.webcrawler.util.CrawlerStatistics;
import com.paul.webcrawler.util.HtmlCrawler;
import com.paul.webcrawler.util.ImageCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Service
public class WebCrawlerService {
	public WebUrlResponse crawlWebPages(WebUrlRequest webUrlRequest) throws Exception {
		
		WebUrlResponse webUrlResponse = new WebUrlResponse();
		
		
		File crawlStorageBase = new File("src/main/resources/crawler4j");
		CrawlConfig htmlConfig = new CrawlConfig();
		CrawlConfig imageConfig = new CrawlConfig();
		        
		// Configure storage folders and other configurations
		htmlConfig.setCrawlStorageFolder(crawlStorageBase.getAbsolutePath());
		imageConfig.setCrawlStorageFolder(crawlStorageBase.getAbsolutePath());
		
		htmlConfig.setMaxDepthOfCrawling(2);
		htmlConfig.setMaxPagesToFetch(500);
		htmlConfig.setIncludeHttpsPages(false);


		imageConfig.setMaxDepthOfCrawling(2);
		imageConfig.setMaxPagesToFetch(500);
		imageConfig.setIncludeBinaryContentInCrawling(true);

		
		PageFetcher pageFetcherHtml = new PageFetcher(htmlConfig);
		PageFetcher pageFetcherImage = new PageFetcher(imageConfig);
		        
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcherHtml);
		 
		CrawlController htmlController = new CrawlController(htmlConfig, pageFetcherHtml, robotstxtServer);
		CrawlController imageController = new CrawlController(imageConfig, pageFetcherImage, robotstxtServer);
		        
		// add seed URLs
		htmlController.addSeed("https://www.disney.com/");
		imageController.addSeed("https://www.disney.com/");

		CrawlerStatistics stats = new CrawlerStatistics();
		//CrawlController.WebCrawlerFactory<HtmlCrawler> htmlFactory = () -> new HtmlCrawler(stats);
		CrawlController.WebCrawlerFactory<HtmlCrawler> htmlFactory = HtmlCrawler::new;
		        
		File saveDir = new File("src/test/resources/crawler4j");
		//CrawlController.WebCrawlerFactory<ImageCrawler> imageFactory = () -> new ImageCrawler(saveDir);
		CrawlController.WebCrawlerFactory<ImageCrawler> imageFactory = ImageCrawler::new;
				
		imageController.startNonBlocking(imageFactory, 8);
		htmlController.startNonBlocking(htmlFactory, 8);

		
		htmlController.waitUntilFinish();
		imageController.waitUntilFinish();	
		
			
		return webUrlResponse;
	}
	
}


