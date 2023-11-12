package com.koombea.scraper.services;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService {

    private RestTemplate restTemplate;

    public ScraperService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<String> scrapeWeb(String url){
        List<String> result = new ArrayList<>();
        String response = this.restTemplate.getForObject(url, String.class);

        try {
             Document doc = Jsoup.connect(url).get();
             Elements elements = doc.getElementsByTag("a");

            for (Element anchor: elements) {
                String link = anchor.attr("href");
                if(link.contains("http") || link.contains("https")){
                    result.add(link);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


}
