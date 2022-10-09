package com.spaceurgent.rickandmortyapp.dto.external.episode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ApiEpisodeDto {
    private Long id;
    private String name;
    @JsonProperty(value = "air_date")
    private String airDate;
    private String episode;
    private List<String> characters;
}
