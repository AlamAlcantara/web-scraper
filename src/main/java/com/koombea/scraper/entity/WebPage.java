package com.koombea.scraper.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "web_page")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "processing_status")
    private ProcessingStatus processingStatus;

    @OneToMany(mappedBy = "webPage")
    @JsonIgnore
    private List<Link> links;
}
