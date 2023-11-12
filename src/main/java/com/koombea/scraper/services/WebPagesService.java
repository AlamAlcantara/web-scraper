package com.koombea.scraper.services;


import com.koombea.scraper.dto.LinkDto;
import com.koombea.scraper.dto.WebPageDto;
import com.koombea.scraper.entity.Link;
import com.koombea.scraper.entity.WebPage;
import com.koombea.scraper.exception.ResourceNotFoundException;
import com.koombea.scraper.repository.LinkRepository;
import com.koombea.scraper.repository.WebPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WebPagesService {

    private WebPageRepository webPageRepository;

    private LinkRepository linkRepository;

    @Autowired
    public WebPagesService(WebPageRepository webPageRepository, LinkRepository linkRepository) {
        this.webPageRepository = webPageRepository;
        this.linkRepository = linkRepository;
    }


    public WebPageDto findById(Integer id) {

        Optional<WebPage> webPage = this.webPageRepository.findById(id);

        if(!webPage.isPresent()) {
            throw new ResourceNotFoundException("Webpage not found");
        }

        WebPageDto dto = WebPageDto.builder()
                .id(webPage.get().getId())
                .name(webPage.get().getName())
                .totalLinks(webPage.get().getLinks().size())
                .url(webPage.get().getUrl())
                .build();

        return dto;

    }

    public Page<WebPageDto> findByUsername(String username, Pageable pageable) {

        Page<WebPage> webPagesPage = this.webPageRepository.findByUsername(username, pageable);
        List<WebPageDto> webPageDtoList = new ArrayList<>();

        for (WebPage webPage : webPagesPage.getContent()) {
            WebPageDto dto = WebPageDto.builder()
                    .id(webPage.getId())
                    .name(webPage.getName())
                    .url(webPage.getUrl())
                    .totalLinks(webPage.getLinks().size())
                    .build();
            webPageDtoList.add(dto);
        }

        Page<WebPageDto> webPageDtoPage = new PageImpl<>(webPageDtoList, pageable, webPagesPage.getTotalElements());

        return webPageDtoPage;
    }

    public Page<LinkDto> getLinksByWebPageId(Integer id, Pageable pageable) {

        Page<Link> linkList = this.linkRepository.findByWebPageId(id, pageable);
        List<LinkDto> linkDtos = new ArrayList<>();

        for (Link l: linkList) {
            LinkDto dto = LinkDto.builder()
                    .url(l.getUrl())
                    .body(l.getBody())
                    .build();
            linkDtos.add(dto);
        }

        Page<LinkDto> linkDtoPage = new PageImpl<>(linkDtos, pageable, linkList.getTotalElements());

        return linkDtoPage;
    }

}
