package com.koombea.scraper.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkDto {
    private String url;
    private String body;
}
