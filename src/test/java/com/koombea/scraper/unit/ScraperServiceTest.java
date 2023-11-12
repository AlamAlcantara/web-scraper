package com.koombea.scraper.unit;

import com.koombea.scraper.entity.User;
import com.koombea.scraper.exception.ScraperException;
import com.koombea.scraper.exception.ResourceNotFoundException;
import com.koombea.scraper.repository.UserRepository;
import com.koombea.scraper.repository.WebPageRepository;
import com.koombea.scraper.services.ScraperService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ScraperServiceTest {

    public WebPageRepository webPageRepository;

    public UserRepository userRepository;

    public ScraperService scraperService;

    @Before
    public void setUp() {
        this.webPageRepository = mock(WebPageRepository.class);
        this.userRepository = mock(UserRepository.class);

        this.scraperService = new ScraperService(webPageRepository, userRepository);
    }

    @Test(expected = ScraperException.class)
    public void testScrapeWebWithMalformedURI(){
        //Setup
        User user = User.builder()
                .id(1)
                .username("John")
                .password("123")
                .build();

        when(this.userRepository.findByUsername("John"))
                .thenReturn(Optional.of(user));

        //execute
        scraperService.scrapeWeb("https://google", "John");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testScrapeWebWithNotFoundUser(){
        //Setup
        when(this.userRepository.findByUsername("John"))
                .thenReturn(Optional.empty());

        //execute
        scraperService.scrapeWeb("https://google.com/", "John");
    }

    @Test()
    public void testScrapeWebWithValidURI() throws IOException {
        //Setup
        User user = User.builder()
                .id(1)
                .username("John")
                .password("123")
                .build();

        Connection connectionMock = mock(Connection.class);
        MockedStatic<Jsoup> jsoupMockedStatic = Mockito.mockStatic(Jsoup.class);

        jsoupMockedStatic
                .when(() -> Jsoup.connect(anyString()))
                .thenReturn(connectionMock);

        when(connectionMock.get()).thenReturn(new Document("https://google.com"));
        when(this.userRepository.findByUsername("John")).thenReturn(Optional.of(user));

        //execute
        scraperService.scrapeWeb("https://google.com", "John");

        //Assert
        Mockito.verify(this.webPageRepository, times(1)).save(any());
    }


}
