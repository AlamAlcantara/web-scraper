package com.koombea.scraper.repository;

import com.koombea.scraper.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {

    @Query(value = "select l from Link l where l.webPage.id = ?1")
    public List<Link> findByWebPageId(Integer id);

}
