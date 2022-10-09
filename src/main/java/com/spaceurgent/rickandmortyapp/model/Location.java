package com.spaceurgent.rickandmortyapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "locations")
public class Location {
    @Id
    private Long id;
    private String name;
    private String type;
    private String dimension;
}
