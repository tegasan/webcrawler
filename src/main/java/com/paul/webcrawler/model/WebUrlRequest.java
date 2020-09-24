package com.paul.webcrawler.model;

//import lombok.Data;

public class WebUrlRequest {
	private String startUrl;
	private String depthCrawl;
	public String getStartUrl() {
		return startUrl;
	}
	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}
	public String getDepthCrawl() {
		return depthCrawl;
	}
	public void setDepthCrawl(String depthCrawl) {
		this.depthCrawl = depthCrawl;
	}
	public WebUrlRequest(String startUrl, String depthCrawl) {
		super();
		this.startUrl = startUrl;
		this.depthCrawl = depthCrawl;
	}
	
	
}
