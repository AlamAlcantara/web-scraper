package com.koombea.scraper.unit;

import com.koombea.scraper.dto.LinkDto;
import com.koombea.scraper.dto.WebPageDto;
import com.koombea.scraper.entity.Link;
import com.koombea.scraper.entity.WebPage;
import com.koombea.scraper.exception.ResourceNotFoundException;
import com.koombea.scraper.repository.LinkRepository;
import com.koombea.scraper.repository.WebPageRepository;
import com.koombea.scraper.services.WebPagesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebPagesServiceTest {

    public WebPageRepository webPageRepository;

    public LinkRepository linkRepository;

    public WebPagesService webPagesService;

    @Before
    public void setUp(){
        this.webPageRepository = mock(WebPageRepository.class);
        this.linkRepository = mock(LinkRepository.class);

        this.webPagesService = new WebPagesService(webPageRepository, linkRepository);
    }

    @Test
    public void testFindById(){
        //Setup
        WebPageDto webPageDto = WebPageDto.builder()
                .id(1)
                .name("Koombea")
                .url("https://koombea.com")
                .totalLinks(0)
                .build();

        WebPage webPage = WebPage.builder()
                .id(1)
                .name("Koombea")
                .url("https://koombea.com")
                .links(Collections.emptyList())
                .build();

        when(webPageRepository.findById(1)).thenReturn(Optional.of(webPage));

        //Execute

        WebPageDto response = this.webPagesService.findById(1);

        //Assert
        assertNotNull(webPageDto);
        assertEquals(response.getId(), webPageDto.getId());
        assertEquals(response.getName(), webPageDto.getName());
        assertEquals(response.getUrl(), webPageDto.getUrl());
        assertEquals(response.getTotalLinks(), webPageDto.getTotalLinks());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindByIdWithException(){
        //Setup
        when(webPageRepository.findById(1)).thenReturn(Optional.empty());

        //Execute
        this.webPagesService.findById(1);
    }

    @Test
    public void testFindByUsername() {
        //Setup
        WebPage webPage1 = WebPage.builder()
                .id(1)
                .name("Koombea")
                .url("https://koombea.com")
                .links(Collections.emptyList())
                .build();

        WebPage webPage2 = WebPage.builder()
                .id(2)
                .name("facebook")
                .url("https://facebook.com")
                .links(Collections.emptyList())
                .build();

        List<WebPage> webPageList = new ArrayList<>();
        webPageList.add(webPage1);
        webPageList.add(webPage2);

        PageRequest pageRequest = PageRequest.of(1,2);
        Page<WebPage> webPagesPage = new PageImpl<>(webPageList, pageRequest, webPageList.size());

        when(webPageRepository.findByUsername("John", pageRequest))
                .thenReturn(webPagesPage);

        //Execute
        Page<WebPageDto> response = this.webPagesService.findByUsername("John", pageRequest);

        //Assert
        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals(response.getContent().get(0).getName(), webPage1.getName());
        assertEquals(response.getContent().get(1).getName(), webPage2.getName());

    }

    @Test
    public void testGetLinksByWebPageId(){
        //SetUp
        Link link1 = Link.builder()
                .id(1)
                .url("https://amazon.com/careers")
                .body("careers")
                .build();

        Link link2 = Link.builder()
                .id(2)
                .url("https://Koombea.com/careers")
                .body("careers")
                .build();

        List<Link> linkList = new ArrayList<>();
        linkList.add(link1);
        linkList.add(link2);

        PageRequest pageRequest = PageRequest.of(1,2);
        Page<Link> linkPage = new PageImpl<>(linkList, pageRequest, linkList.size());

        when(linkRepository.findByWebPageId(1, pageRequest)).thenReturn(linkPage);

        //Execute
        Page<LinkDto> response = this.webPagesService.getLinksByWebPageId(1, pageRequest);

        //Assert
        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals(link1.getUrl(), response.getContent().get(0).getUrl());
        assertEquals(link2.getUrl(), response.getContent().get(1).getUrl());

    }
}
