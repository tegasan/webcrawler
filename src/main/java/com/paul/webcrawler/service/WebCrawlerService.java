package com.paul.webcrawler.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paul.webcrawler.controller.repository.WebCrawlerRepository;
import com.paul.webcrawler.entity.PagesEntity;
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
	
	@Autowired
	WebCrawlerRepository webCrawlerRepository;
	
	public WebUrlResponse crawlWebPages(WebUrlRequest webUrlRequest) throws Exception {

		WebUrlResponse webUrlResponse = new WebUrlResponse();
		
		// Configure storage folders and other configurations
		File crawlStorageBaseHtml = new File("src/main/resources/crawler4jHtml");
		File crawlStorageBaseImage = new File("src/main/resources/crawler4jImage");
		
		//html config
		CrawlConfig htmlConfig = new CrawlConfig();
		htmlConfig.setCrawlStorageFolder(crawlStorageBaseHtml.getAbsolutePath());
		htmlConfig.setMaxDepthOfCrawling(1);
		htmlConfig.setMaxPagesToFetch(200);

		htmlConfig.setCleanupDelaySeconds(2);		
		htmlConfig.setThreadShutdownDelaySeconds(2);
		htmlConfig.setIncludeHttpsPages(true);		

		//image config		
		CrawlConfig imageConfig = new CrawlConfig();
		imageConfig.setCrawlStorageFolder(crawlStorageBaseImage.getAbsolutePath());
		imageConfig.setMaxDepthOfCrawling(1);
		imageConfig.setMaxPagesToFetch(200);

		imageConfig.setCleanupDelaySeconds(2);
		imageConfig.setThreadShutdownDelaySeconds(2);
		imageConfig.setIncludeBinaryContentInCrawling(true);		
		
		PageFetcher pageFetcherHtml = new PageFetcher(htmlConfig);
		PageFetcher pageFetcherImage = new PageFetcher(imageConfig);
		        
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcherHtml);
		 
		CrawlController htmlController = new CrawlController(htmlConfig, pageFetcherHtml, robotstxtServer);
		CrawlController imageController = new CrawlController(imageConfig, pageFetcherImage, robotstxtServer);				       
		
		// add seed URLs
		htmlController.addSeed("https://www.wipro.com/");
		imageController.addSeed("https://www.wipro.com/");

		PagesEntity pagesEntity = new PagesEntity(); 
		List<PagesEntity> listPagesEntity = new ArrayList<PagesEntity>();
		
		CrawlerStatistics stats = new CrawlerStatistics();
		CrawlController.WebCrawlerFactory<HtmlCrawler> htmlFactory = () -> new HtmlCrawler(stats, listPagesEntity);
		        
		File saveDir = new File("src/test/resources/crawler4j");
		CrawlController.WebCrawlerFactory<ImageCrawler> imageFactory = () -> new ImageCrawler(saveDir, listPagesEntity);
				
		imageController.startNonBlocking(imageFactory, 8);
		htmlController.startNonBlocking(htmlFactory, 8);

		
		htmlController.waitUntilFinish();
		imageController.waitUntilFinish();	
		webCrawlerRepository.saveAll(listPagesEntity);	
			
		return webUrlResponse;
	}
	
}


