package com.koombea.scraper.repository;

import com.koombea.scraper.entity.WebPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageRepository extends JpaRepository<WebPage, Integer> {
}
