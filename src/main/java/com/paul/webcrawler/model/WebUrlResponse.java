package com.paul.webcrawler.model;

import lombok.Data;

@Data
public class WebUrlResponse {
	String domain;
	String url;
	String type;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public WebUrlResponse() {
		super();		
	}
	public WebUrlResponse(String domain, String url, String type) {
		super();
		this.domain = domain;
		this.url = url;
		this.type = type;
	}
	
}
