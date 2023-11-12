package com.koombea.scraper.repository;

import com.koombea.scraper.entity.WebPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebPageRepository extends JpaRepository<WebPage, Integer> {

    @Query(value = "select wp from WebPage wp where wp.user.username = ?1")
    public List<WebPage> findByUsername(String username);

}
