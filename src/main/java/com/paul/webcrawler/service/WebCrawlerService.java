package com.paul.webcrawler.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	
	public void crawlWebPages(WebUrlRequest webUrlRequest) throws Exception {

		WebUrlResponse webUrlResponse = new WebUrlResponse();
		
		// Clean the table
		webCrawlerRepository.deleteAll();
		
		String urlSeed = webUrlRequest.getStartUrl();
		int crawlDepth = Integer.parseInt(webUrlRequest.getDepthCrawl());
		System.out.println("urlSeed: " + urlSeed);
		System.out.println("crawlDepth: " + crawlDepth);
		
		// Configure storage folders and other configurations
		File crawlStorageBaseHtml = new File("src/main/resources/crawler4jHtml");
		File crawlStorageBaseImage = new File("src/main/resources/crawler4jImage");
		
		//html config
		CrawlConfig htmlConfig = new CrawlConfig();
		htmlConfig.setCrawlStorageFolder(crawlStorageBaseHtml.getAbsolutePath());
		htmlConfig.setMaxDepthOfCrawling(crawlDepth);
		htmlConfig.setMaxPagesToFetch(200);

		htmlConfig.setCleanupDelaySeconds(1);		
		htmlConfig.setThreadShutdownDelaySeconds(1);
		htmlConfig.setConnectionTimeout(2000);
		htmlConfig.setSocketTimeout(2000);
		htmlConfig.setIncludeHttpsPages(true);		

		//image config		
		CrawlConfig imageConfig = new CrawlConfig();
		imageConfig.setCrawlStorageFolder(crawlStorageBaseImage.getAbsolutePath());
		imageConfig.setMaxDepthOfCrawling(crawlDepth);
		imageConfig.setMaxPagesToFetch(500);

		imageConfig.setCleanupDelaySeconds(1);
		imageConfig.setThreadShutdownDelaySeconds(1);
		imageConfig.setConnectionTimeout(2000);
		imageConfig.setSocketTimeout(2000);
		imageConfig.setIncludeBinaryContentInCrawling(true);		
		
		PageFetcher pageFetcherHtml = new PageFetcher(htmlConfig);
		PageFetcher pageFetcherImage = new PageFetcher(imageConfig);
		        
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcherHtml);
		 
		CrawlController htmlController = new CrawlController(htmlConfig, pageFetcherHtml, robotstxtServer);
		CrawlController imageController = new CrawlController(imageConfig, pageFetcherImage, robotstxtServer);				       
		
		// add seed URLs
		htmlController.addSeed(urlSeed);
		imageController.addSeed(urlSeed);

		PagesEntity pagesEntity = new PagesEntity(); 
		List<PagesEntity> listPagesEntity = new ArrayList<PagesEntity>();
		
		CrawlerStatistics stats = new CrawlerStatistics();
		CrawlController.WebCrawlerFactory<HtmlCrawler> htmlFactory = () -> new HtmlCrawler(stats, listPagesEntity, urlSeed);
		        
		File saveDir = new File("src/test/resources/crawler4j");
		CrawlController.WebCrawlerFactory<ImageCrawler> imageFactory = () -> new ImageCrawler(saveDir, listPagesEntity, urlSeed);
				
		imageController.startNonBlocking(imageFactory, 8);
		htmlController.startNonBlocking(htmlFactory, 8);

		
		htmlController.waitUntilFinish();
		imageController.waitUntilFinish();
		webCrawlerRepository.saveAll(listPagesEntity);	
		htmlController.shutdown();
		imageController.shutdown();
		
		//return webUrlResponse;
	}
	
	public List<PagesEntity> getResult() {

		WebUrlResponse webUrlResponse = new WebUrlResponse();
		//PagesEntity pagesEntity = new PagesEntity(); 
		List<PagesEntity> listPagesEntity = new ArrayList<PagesEntity>();		

		listPagesEntity = webCrawlerRepository.findAll(Sort.by("type").descending());
		
		return listPagesEntity;
	}
}


