package com.spaceurgent.rickandmortyapp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    private Long id;
    private String name;
    @Column(name = "air_date")
    private LocalDate airDate;
    private String episode;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "episodes_characters",
            joinColumns = @JoinColumn(name = "episode_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<MovieCharacter> characters;
}
