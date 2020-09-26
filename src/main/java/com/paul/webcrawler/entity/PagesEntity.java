package com.paul.webcrawler.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="pages")
public class PagesEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	private String url;
	private String type;
	private LocalDateTime crawlDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public LocalDateTime getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(LocalDateTime crawlDate) {
		this.crawlDate = crawlDate;
	}
	public PagesEntity() {
		super();

	}
	public void setCrawl_date(LocalDateTime dateTime) {
		// TODO Auto-generated method stub
		
	}
	public PagesEntity(Long id, String url, String type, LocalDateTime crawlDate) {
		super();
		this.id = id;
		this.url = url;
		this.type = type;
		this.crawlDate = crawlDate;
	}
	
	
}
