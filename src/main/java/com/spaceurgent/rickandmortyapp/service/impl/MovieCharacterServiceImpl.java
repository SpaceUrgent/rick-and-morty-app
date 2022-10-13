package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.dto.external.charcter.ApiCharactersResponseDto;
import com.spaceurgent.rickandmortyapp.dto.external.location.ApiLocationResponseDto;
import com.spaceurgent.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.repository.MovieCharacterRepository;
import com.spaceurgent.rickandmortyapp.service.HttpClient;
import com.spaceurgent.rickandmortyapp.service.MovieCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final MovieCharacterRepository movieCharacterRepository;
    private final HttpClient httpClient;
    private final MovieCharacterMapper movieCharacterMapper;
    @Value("${app.characters.url}")
    private String charactersUrl;

    @Autowired
    public MovieCharacterServiceImpl(MovieCharacterRepository movieCharacterRepository, HttpClient httpClient, MovieCharacterMapper movieCharacterMapper) {
        this.movieCharacterRepository = movieCharacterRepository;
        this.httpClient = httpClient;
        this.movieCharacterMapper = movieCharacterMapper;
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

    @Override
    public void syncMovieCharacters() {
        List<ApiCharactersResponseDto> responseDtos = httpClient.getResultsFromApi(charactersUrl, ApiCharactersResponseDto.class);
        saveAll(responseDtos.stream()
                .map(ApiCharactersResponseDto::getResults)
                .flatMap(Arrays::stream)
                .map(movieCharacterMapper::toModel)
                .collect(Collectors.toList()));
    }

    @Override
    public List<MovieCharacter> getAll(PageRequest pageRequest) {
        return movieCharacterRepository.findAll(pageRequest).toList();
    }

    @Override
    public Long countPages() {
        return Long.valueOf(movieCharacterRepository.count() / 20);
    }

    @Override
    public List<MovieCharacter> findByNameOrLocation(String value, PageRequest pageRequest) {
        return movieCharacterRepository
                        .findAllByNameOrLocation(value,
                                pageRequest)
                        .getContent();
    }
}
