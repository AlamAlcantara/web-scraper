package com.koombea.scraper.dto;


import com.koombea.scraper.entity.ProcessingStatus;
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
    private String processingStatus;

}
