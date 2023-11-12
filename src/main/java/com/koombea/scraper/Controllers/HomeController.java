package com.koombea.scraper.Controllers;


import com.koombea.scraper.dto.LinkDto;
import com.koombea.scraper.dto.UrlDto;
import com.koombea.scraper.dto.WebPageDto;
import com.koombea.scraper.entity.User;
import com.koombea.scraper.services.ScraperService;
import com.koombea.scraper.services.WebPagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/links")
public class HomeController {

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private WebPagesService webPagesService;

    @GetMapping
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("PRINCIPAL" + authentication.getName());

        List<WebPageDto> webPageDtoList = this.webPagesService.findByUsername(authentication.getName());
        model.addAttribute("pages", webPageDtoList);

        return "links";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") Integer webPageId, Model model){
        List<LinkDto> linkDtos = this.webPagesService.getLinksByWebPageId(webPageId);
        model.addAttribute("links", linkDtos);

        return "details";
    }

    @PostMapping
    public String scrape(@ModelAttribute("url") UrlDto url, Model model){
        System.out.println("URL: " + url);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("PRINCIPAL" + authentication.getName());

        this.scraperService.scrapeWeb(url.getUrl(), authentication.getName());
        List<WebPageDto> webPageDtoList = this.webPagesService.findByUsername(authentication.getName());
        model.addAttribute("pages", webPageDtoList);

        return "redirect:/links";
    }

}
