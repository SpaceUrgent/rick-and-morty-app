package com.spaceurgent.rickandmortyapp.dto.mapper;

import com.spaceurgent.rickandmortyapp.dto.external.episode.ApiEpisodeDto;
import com.spaceurgent.rickandmortyapp.model.Episode;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.service.MovieCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EpisodeMapper implements RequestMapper<Episode, ApiEpisodeDto> {
    private final MovieCharacterService movieCharacterService;

    @Autowired
    public EpisodeMapper(MovieCharacterService movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    @Override
    public Episode toModel(ApiEpisodeDto dto) {
        Episode episode = new Episode();
        episode.setId(dto.getId());
        episode.setName(dto.getName());
        episode.setAirDate(LocalDate.parse(dto.getAirDate(), DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        episode.setEpisode(dto.getEpisode());
        List<String> charactersUrl = dto.getCharacters();
        List<MovieCharacter> movieCharacters = charactersUrl.stream()
                .map(this::getCharacter)
                .collect(Collectors.toList());
        episode.setCharacters(movieCharacters);
        return episode;
    }

    private MovieCharacter getCharacter(String characterUrl) {
        String[] url = characterUrl.split("/");
        return movieCharacterService.findById(Long.parseLong(url[url.length - 1]));
    }
}
