package com.paul.webcrawler.util;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.paul.webcrawler.entity.PagesEntity;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class ImageCrawler extends WebCrawler {
    //private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|png|mp3|mp4|zip|gz|pdf))$");
    private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|mp3|mp4|zip|gz|pdf))$");
    private static final Pattern IMG_PATTERNS = Pattern.compile(".*(\\.(jpg|jpeg|gif|png))$");
	private File saveDir;
	private List<PagesEntity> listPagesEntity = new ArrayList<PagesEntity>();
	private String urlSeed=null;
   
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
	    String urlString = url.getURL().toLowerCase();
	    if (EXCLUSIONS.matcher(urlString).matches()) {
	        return false;
	    }
	    
	    if (IMG_PATTERNS.matcher(urlString).matches() 
	        || urlString.startsWith(urlSeed)) {
	        return true;
	    }
 
	    return false;
	}
    
	@Override
	public void visit(Page page) { 
	    LocalDateTime dateTime = LocalDateTime.now(); 
	    String url = page.getWebURL().getURL();
	    if (IMG_PATTERNS.matcher(url).matches() 
	        && page.getParseData() instanceof BinaryParseData) {
	        String extension = url.substring(url.lastIndexOf("."));
	        int contentLength = page.getContentData().length;
	 
	        // write the content data to a file in the save directory
	        System.out.println("image url:" + url + " | size:" + contentLength + " | type: " + extension);
	        PagesEntity pagesEntity = new PagesEntity();
	        pagesEntity.setCrawlDate(dateTime);
	        pagesEntity.setType("IMAGE URL");
	        url = url.length()>255?url.substring(0, 255):url;
	        pagesEntity.setUrl(url);
	        listPagesEntity.add(pagesEntity);
	    }
	}

	public File getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(File saveDir) {
		this.saveDir = saveDir;
	}

	public List<PagesEntity> getListPagesEntity() {
		return listPagesEntity;
	}

	public void setListPagesEntity(List<PagesEntity> listPagesEntity) {
		this.listPagesEntity = listPagesEntity;
	}

	
    public String getUrlSeed() {
		return urlSeed;
	}

	public void setUrlSeed(String urlSeed) {
		this.urlSeed = urlSeed;
	}

	public ImageCrawler() {
		super();
	}

    public ImageCrawler(File saveDir, List<PagesEntity> listPagesEntity, String urlSeed) {
		//super();
		this.saveDir = saveDir;
		this.listPagesEntity = listPagesEntity;
		this.urlSeed = urlSeed;
	}
}