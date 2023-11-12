package com.koombea.scraper.Controllers;


import com.koombea.scraper.dto.LinkDto;
import com.koombea.scraper.dto.UrlDto;
import com.koombea.scraper.dto.WebPageDto;
import com.koombea.scraper.services.ScraperService;
import com.koombea.scraper.services.WebPagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/links")
public class HomeController {

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private WebPagesService webPagesService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "3") int size){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Pageable paging = PageRequest.of(page-1,size);
        Page<WebPageDto> webPageDtoList = this.webPagesService.findByUsername(authentication.getName(), paging);

        model.addAttribute("pages", webPageDtoList.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", webPageDtoList.getTotalPages());

        return "links";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") Integer webPageId,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "3") int size,
                          Model model) {

        Pageable paging = PageRequest.of(page-1,size);
        WebPageDto webPageDto = this.webPagesService.findById(webPageId);
        Page<LinkDto> linkDtos = this.webPagesService.getLinksByWebPageId(webPageId, paging);

        model.addAttribute("pageTitle", webPageDto.getName());
        model.addAttribute("links", linkDtos.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", linkDtos.getTotalPages());

        return "details";
    }

    @PostMapping
    public String scrape(@ModelAttribute("url") UrlDto url, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        this.scraperService.scrapeWeb(url.getUrl(), authentication.getName());

        Pageable paging = PageRequest.of(0,3);
        Page<WebPageDto> webPageDtoList = this.webPagesService.findByUsername(authentication.getName(), paging);

        model.addAttribute("pages", webPageDtoList.getContent());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", webPageDtoList.getTotalPages());

        return "redirect:/links";
    }

}
