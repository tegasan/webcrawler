package com.paul.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WebCrawlerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawlerApplication.class, args);
	}

}
