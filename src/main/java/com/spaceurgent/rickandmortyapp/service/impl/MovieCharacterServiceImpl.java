package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.repository.MovieCharacterRepository;
import com.spaceurgent.rickandmortyapp.service.MovieCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final MovieCharacterRepository movieCharacterRepository;

    @Autowired
    public MovieCharacterServiceImpl(MovieCharacterRepository movieCharacterRepository) {
        this.movieCharacterRepository = movieCharacterRepository;
    }

    @Override
    public MovieCharacter save(MovieCharacter movieCharacter) {
        return movieCharacterRepository.save(movieCharacter);
    }

    @Override
    public void saveAll(List<MovieCharacter> movieCharacters) {
        movieCharacterRepository.saveAll(movieCharacters);
    }

    @Override
    public MovieCharacter findById(Long id) {
        return movieCharacterRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find character with id: " + id)
        );
    }
}
