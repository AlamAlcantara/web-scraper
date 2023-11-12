package com.koombea.scraper.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "processing_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "processingStatus")
    @JsonIgnore
    private List<WebPage> webPages;

}
