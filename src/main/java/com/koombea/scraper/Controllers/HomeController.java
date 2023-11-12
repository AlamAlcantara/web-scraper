package com.koombea.scraper.Controllers;


import com.koombea.scraper.entity.User;
import com.koombea.scraper.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/links")
public class HomeController {

    @Autowired
    private ScraperService scraperService;

    @GetMapping
    public String hello(){
//        return new ResponseEntity<>(this.scraperService.scrapeWeb("https://www.amazon.com/"), HttpStatus.OK);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("PRINCIPAL" + authentication.getName());

        return "links";
    }

//    @PostMapping
//    public ResponseEntity<List<String>> scrape(@RequestBody UrlDto url){
//        System.out.println("URL: " + url);
//        return new ResponseEntity<>(this.scraperService.scrapeWeb(url.getUrl()), HttpStatus.OK);
//    }

}
