package com.koombea.scraper.services;


import com.koombea.scraper.dto.LinkDto;
import com.koombea.scraper.dto.WebPageDto;
import com.koombea.scraper.entity.Link;
import com.koombea.scraper.entity.WebPage;
import com.koombea.scraper.repository.LinkRepository;
import com.koombea.scraper.repository.WebPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebPagesService {

    @Autowired
    private WebPageRepository webPageRepository;

    @Autowired
    private LinkRepository linkRepository;


    public List<WebPageDto> findByUsername(String username) {
        List<WebPageDto> webPageDtoList = new ArrayList<>();
        List<WebPage> webPages = this.webPageRepository.findByUsername(username);

        for (WebPage webPage : webPages) {
            WebPageDto dto = WebPageDto.builder()
                    .id(webPage.getId())
                    .name(webPage.getName())
                    .url(webPage.getUrl())
                    .processingStatus(webPage.getProcessingStatus().getDescription())
                    .totalLinks(webPage.getLinks().size())
                    .build();
            webPageDtoList.add(dto);
        }

        return webPageDtoList;
    }

    public List<LinkDto> getLinksByWebPageId(Integer id) {
        List<LinkDto> linkDtos = new ArrayList<>();

        List<Link> linkList = this.linkRepository.findByWebPageId(id);

        for (Link l: linkList) {
            LinkDto dto = LinkDto.builder()
                    .url(l.getUrl())
                    .body(l.getBody())
                    .build();
            linkDtos.add(dto);
        }

        return linkDtos;
    }

}
