package com.spaceurgent.rickandmortyapp.service;

import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MovieCharacterService {
    MovieCharacter save(MovieCharacter movieCharacter);

    void saveAll(List<MovieCharacter> movieCharacters);

    MovieCharacter findById(Long id);

    void syncMovieCharacters();

    List<MovieCharacter> getAll(PageRequest pageRequest);
}
