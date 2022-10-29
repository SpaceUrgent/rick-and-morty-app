package com.spaceurgent.rickandmortyapp.service;

import com.spaceurgent.rickandmortyapp.model.Location;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.model.enums.Status;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MovieCharacterService {
    MovieCharacter save(MovieCharacter movieCharacter);

    void saveAll(List<MovieCharacter> movieCharacters);

    MovieCharacter findById(Long id);

    void syncMovieCharacters();

    List<MovieCharacter> getAll(PageRequest pageRequest);

    Long countPages(Integer count);

    Long countPages(Integer count, String namePattern);
    Long countPages(Integer count, String status, String namePattern);
    Long countPagesByResidence(Integer count, Long residenceId);
    Long countPagesByOrigin(Integer count, Long originId);
    List<MovieCharacter> findAllByNameContains(String value, PageRequest pageRequest);

    List<MovieCharacter> finaAllByStatus(String status, PageRequest pageRequest);
    List<MovieCharacter> findAllByNameContainsAndStatus(String status, String value, PageRequest pageRequest);
    Long countByResidence(Long residenceId);
    Long countByOrigin(Long originId);
    List<MovieCharacter> findAllByResidence(Long locationId, PageRequest pageRequest);
    List<MovieCharacter> findAllByOrigin(Long locationId, PageRequest pageRequest);


    Long countMovieCharactersPagesByEpisodeId(Long id, Integer count);


    List<MovieCharacter> findMovieCharactersByEpisodeId(Long episodeId, Integer page, Integer count);
}
