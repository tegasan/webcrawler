package com.paul.webcrawler.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.paul.webcrawler.entity.PagesEntity;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class HtmlCrawler extends WebCrawler {

	private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
	private List<PagesEntity> listPagesEntity = new ArrayList<PagesEntity>();
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
	    String urlString = url.getURL().toLowerCase();  
	    return !EXCLUSIONS.matcher(urlString).matches() 
	      && urlString.startsWith("https://www.wipro.com");
	}
	
	@Override
	public void visit(Page page) {
	    String url = page.getWebURL().getURL();
	    LocalDateTime dateTime = LocalDateTime.now();
	    if (page.getParseData() instanceof HtmlParseData) {
	        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	        //String title = htmlParseData.getTitle();
	        //String text = htmlParseData.getText();
	        //String html = htmlParseData.getHtml();
	        Set<WebURL> links = htmlParseData.getOutgoingUrls();
	 
	        System.out.println("webpage url: " + url); 
	        System.out.println("dateTime: " + dateTime);
			// do something with the collected data
	        PagesEntity pagesEntity = new PagesEntity();
	        pagesEntity.setCrawl_date(dateTime);
	        pagesEntity.setType("WEB URL");
	        pagesEntity.setUrl(url);
	        listPagesEntity.add(pagesEntity);
	    }
	}

	public List<PagesEntity> getListPagesEntity() {
		return listPagesEntity;
	}

	public void setListPagesEntity(List<PagesEntity> listPagesEntity) {
		this.listPagesEntity = listPagesEntity;
	}
	
	public HtmlCrawler() {
		super();
	}

	public HtmlCrawler(CrawlerStatistics stats, List<PagesEntity> listPagesEntity) {
		super();
		this.listPagesEntity = listPagesEntity;
	}	
}