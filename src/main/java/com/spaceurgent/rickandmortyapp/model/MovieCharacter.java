package com.spaceurgent.rickandmortyapp.model;

import com.spaceurgent.rickandmortyapp.model.enums.Gender;
import com.spaceurgent.rickandmortyapp.model.enums.Status;
import liquibase.datatype.core.BlobType;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "characters", schema = "rick_and_morty")
public class MovieCharacter {
    @Id
    private Long id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private String spieces;
    private String type;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id")
    private Location origin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @Lob
    private byte[] image;
}
