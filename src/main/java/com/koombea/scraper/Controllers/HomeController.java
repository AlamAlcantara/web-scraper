package com.koombea.scraper.Controllers;


import com.koombea.scraper.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ScraperService scraperService;

    @GetMapping
    public ResponseEntity<List<String>> hello(){
        return new ResponseEntity<>(this.scraperService.scrapeWeb("https://www.amazon.com/"), HttpStatus.OK);
    }

}
