package com.koombea.scraper.services;

import com.koombea.scraper.entity.Link;
import com.koombea.scraper.entity.User;
import com.koombea.scraper.entity.WebPage;
import com.koombea.scraper.exception.ScraperException;
import com.koombea.scraper.exception.ResourceNotFoundException;
import com.koombea.scraper.repository.UserRepository;
import com.koombea.scraper.repository.WebPageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScraperService {

    private WebPageRepository webPageRepository;

    private UserRepository userRepository;

    @Autowired
    public ScraperService(WebPageRepository webPageRepository, UserRepository userRepository) {
        this.webPageRepository = webPageRepository;
        this.userRepository = userRepository;
    }

    public void scrapeWeb(String url, String username){
        Optional<User> user = this.userRepository.findByUsername(username);

        if(!user.isPresent()){
           throw new ResourceNotFoundException("User not found");
        }

        try {
             Document doc = Jsoup.connect(url).get();

             WebPage webPage = WebPage.builder()
                     .name(doc.title())
                     .url(url)
                     .user(user.get())
                     .build();

             Elements anchors = doc.getElementsByTag("a");
             List<Link> links = new ArrayList<>();

            for (Element anchor: anchors) {
                String anchorUrl = anchor.attr("href");
                if(anchorUrl.contains("http://") || anchorUrl.contains("https://")){
                    Link link = Link.builder()
                            .webPage(webPage)
                            .url(anchorUrl)
                            .body(anchor.text().length() > 200 ? anchor.text().substring(0,199) : anchor.text())
                            .build();

                    links.add(link);
                }
            }

            webPage.setLinks(links);
            this.webPageRepository.save(webPage);

        } catch (IOException e) {
            throw new ScraperException(e.getMessage());
        }
    }


}
