package com.paul.webcrawler.util;

import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class HtmlCrawler extends WebCrawler {

	private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");

	public HtmlCrawler(CrawlerStatistics stats) {
		// TODO Auto-generated constructor stub
	}

	
	public HtmlCrawler() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
	    String urlString = url.getURL().toLowerCase();  
	    return !EXCLUSIONS.matcher(urlString).matches() 
	      && urlString.startsWith("https://www.wipro.com");
	    
	}
	
	@Override
	public void visit(Page page) {
	    String url = page.getWebURL().getURL();
	 
	    if (page.getParseData() instanceof HtmlParseData) {
	        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	        String title = htmlParseData.getTitle();
	        String text = htmlParseData.getText();
	        String html = htmlParseData.getHtml();
	        Set<WebURL> links = htmlParseData.getOutgoingUrls();
	 
	        System.out.println("webpage url: " + url); 
	        // do something with the collected data
	    
	    }
	}
	
}