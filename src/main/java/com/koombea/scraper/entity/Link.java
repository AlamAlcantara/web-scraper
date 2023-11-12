package com.koombea.scraper.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "link")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JoinColumn(name = "web_page")
    private WebPage webPage;

}
