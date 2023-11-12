package com.koombea.scraper.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "web_page")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "webPage", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Link> links;
}
