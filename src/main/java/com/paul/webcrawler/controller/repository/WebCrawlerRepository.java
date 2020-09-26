package com.paul.webcrawler.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.webcrawler.entity.PagesEntity;

public interface WebCrawlerRepository extends JpaRepository<PagesEntity, Long> {

}
