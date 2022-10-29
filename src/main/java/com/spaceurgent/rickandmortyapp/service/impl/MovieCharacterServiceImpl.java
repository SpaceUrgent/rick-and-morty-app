package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.dto.external.charcter.ApiCharactersResponseDto;
import com.spaceurgent.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.model.enums.Status;
import com.spaceurgent.rickandmortyapp.repository.MovieCharacterRepository;
import com.spaceurgent.rickandmortyapp.service.EpisodeService;
import com.spaceurgent.rickandmortyapp.service.HttpClient;
import com.spaceurgent.rickandmortyapp.service.LocationService;
import com.spaceurgent.rickandmortyapp.service.MovieCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final LocationService locationService;
    @Value("${app.characters.url}")
    private String charactersUrl;

    @Autowired
    public MovieCharacterServiceImpl(MovieCharacterRepository movieCharacterRepository,
                                     HttpClient httpClient,
                                     MovieCharacterMapper movieCharacterMapper,
                                     LocationService locationService) {
        this.movieCharacterRepository = movieCharacterRepository;
        this.httpClient = httpClient;
        this.movieCharacterMapper = movieCharacterMapper;
        this.locationService = locationService;
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
    public Long countPages(Integer count) {
        return Long.valueOf(movieCharacterRepository.count() / count);
    }

    @Override
    public Long countPages(Integer count, String namePattern) {
        return movieCharacterRepository.countAllByNameContains(namePattern) / count;
    }

    @Override
    public Long countPages(Integer count, String status, String namePattern) {
        if ((status == null || status.equals("all")) && namePattern == null) {
            return countPages(count);
        }
        if (status == null || status.equals("all")) {
            return countPages(count, namePattern);
        }
        return movieCharacterRepository
                .countAllByStatusAndNameContains(Status.valueOf(status.toUpperCase()), namePattern) / count;
    }

    @Override
    public Long countPagesByResidence(Integer count, Long residenceId) {
        return countByResidence(residenceId) / count;
    }

    @Override
    public Long countPagesByOrigin(Integer count, Long originId) {
        return countByOrigin(originId) / count;
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String value, PageRequest pageRequest) {
        if (value == null || value.isEmpty()) {
            return getAll(pageRequest);
        }
        return movieCharacterRepository
                        .findAllByNameContains(value,
                                pageRequest)
                        .getContent();
    }

    @Override
    public List<MovieCharacter> finaAllByStatus(String status, PageRequest pageRequest) {
        if (status == null || status.equals("all")) {
            return getAll(pageRequest);
        }
        return movieCharacterRepository
                .findAllByStatus(Status.valueOf(status.toUpperCase()), pageRequest)
                .getContent();
    }

    @Override
    public List<MovieCharacter> findAllByNameContainsAndStatus(String status, String value, PageRequest pageRequest) {
        if (status == null || status.equals("all")) {
            return findAllByNameContains(value, pageRequest);
        }
        return movieCharacterRepository
                .findAllByStatusAndNameContains(Status.valueOf(status.toUpperCase()),
                        value, pageRequest).getContent();
    }

    @Override
    public Long countByResidence(Long residenceId) {
        return movieCharacterRepository.countAllByLocation(locationService.findById(residenceId));
    }

    @Override
    public Long countByOrigin(Long originId) {
        return movieCharacterRepository.countAllByOrigin(locationService.findById(originId));
    }

    @Override
    public List<MovieCharacter> findAllByResidence(Long locationId, PageRequest pageRequest) {
        return movieCharacterRepository
                .findAllByLocation(locationService.findById(locationId), pageRequest)
                .getContent();
    }

    @Override
    public List<MovieCharacter> findAllByOrigin(Long locationId, PageRequest pageRequest) {
        return movieCharacterRepository
                .findAllByOrigin(locationService.findById(locationId), pageRequest)
                .getContent();
    }

    @Override
    public Long countMovieCharactersPagesByEpisodeId(Long id, Integer count) {
        return movieCharacterRepository.countMovieCharacterByEpisodeId(id) / count;
    }

    @Override
    public List<MovieCharacter> findMovieCharactersByEpisodeId(Long episodeId, Integer page, Integer count) {
        if (episodeId == null) {
            throw new RuntimeException("Episode Id can't be null");
        }
        int limitFrom = (page == 0) ? 1 : (page * count);
        int limitTo = limitFrom + count;
        return movieCharacterRepository.findMovieCharactersByEpisodeId(episodeId, limitFrom, limitTo);
    }
}
