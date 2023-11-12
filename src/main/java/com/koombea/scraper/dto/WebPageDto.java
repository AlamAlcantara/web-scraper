package com.koombea.scraper.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebPageDto {

    private int id;
    private String name;
    private String url;
    private int totalLinks;
}
