package com.spaceurgent.rickandmortyapp.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EpisodeDto {
    private Long id;
    private String name;
    private LocalDate airDate;
    private String episode;
    private int charactersNumber;
}
