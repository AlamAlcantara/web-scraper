package com.koombea.scraper.repository;

import com.koombea.scraper.entity.ProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingStatusRepository extends JpaRepository<ProcessingStatus, Integer> {

    ProcessingStatus findByDescription(String description);
}
