package com.koombea.scraper.repository;

import com.koombea.scraper.entity.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {

    @Query(value = "select l from Link l where l.webPage.id = ?1")
    Page<Link> findByWebPageId(Integer id, Pageable pageable);

}
