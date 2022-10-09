package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.dto.external.charcter.ApiCharactersResponseDto;
import com.spaceurgent.rickandmortyapp.dto.external.charcter.ApiMovieCharacterDto;
import com.spaceurgent.rickandmortyapp.dto.external.episode.ApiEpisodeDto;
import com.spaceurgent.rickandmortyapp.dto.external.episode.ApiEpisodeResponseDto;
import com.spaceurgent.rickandmortyapp.dto.external.location.ApiLocationDto;
import com.spaceurgent.rickandmortyapp.dto.external.location.ApiLocationResponseDto;
import com.spaceurgent.rickandmortyapp.dto.mapper.ApiEpisodeMapper;
import com.spaceurgent.rickandmortyapp.dto.mapper.ApiLocationMapper;
import com.spaceurgent.rickandmortyapp.dto.mapper.ApiMovieCharacterMapper;
import com.spaceurgent.rickandmortyapp.repository.EpisodeRepository;
import com.spaceurgent.rickandmortyapp.repository.LocationRepository;
import com.spaceurgent.rickandmortyapp.repository.MovieCharacterRepository;
import com.spaceurgent.rickandmortyapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Service
public class SynchronizationServiceImpl implements SynchronizationService {
    private final LocationService locationService;
    private final MovieCharacterService movieCharacterService;
    private final EpisodeService episodeService;
    private final HttpClient httpClient;
    private final ApiLocationMapper apiLocationMapper;
    private final ApiMovieCharacterMapper apiMovieCharacterMapper;
    private final ApiEpisodeMapper apiEpisodeMapper;

    @Value("${app.characters.url}")
    private String charactersUrl;
    @Value("${app.locations.url}")
    private String locationsUrl;
    @Value("${app.episodes.url}")
    private String episodesUrl;

    @Autowired
    public SynchronizationServiceImpl(LocationRepository locationRepository,
                                      MovieCharacterRepository movieCharacterRepository,
                                      EpisodeRepository episodeRepository,
                                      LocationService locationService,
                                      MovieCharacterService movieCharacterService,
                                      EpisodeService episodeService,
                                      HttpClient httpClient,
                                      ApiLocationMapper apiLocationMapper,
                                      ApiMovieCharacterMapper apiMovieCharacterMapper,
                                      ApiEpisodeMapper apiEpisodeMapper) {
        this.locationService = locationService;
        this.movieCharacterService = movieCharacterService;
        this.episodeService = episodeService;
        this.httpClient = httpClient;
        this.apiLocationMapper = apiLocationMapper;
        this.apiMovieCharacterMapper = apiMovieCharacterMapper;
        this.apiEpisodeMapper = apiEpisodeMapper;
    }

    @Scheduled(cron = "0 0 23 * * ?")
    @Override
    public void syncData() {
        syncLocations();
        syncMovieCharacters();
        syncEpisodes();
    }

    private void syncLocations() {
        ApiLocationResponseDto apiLocationResponseDto;
        String currentUrl = locationsUrl;
        List<ApiLocationDto> locations = new ArrayList<>();
        do {
            apiLocationResponseDto = httpClient.sendGet(currentUrl, ApiLocationResponseDto.class);
            currentUrl = apiLocationResponseDto.getInfo().getNext();
            locations.addAll(Arrays.asList(apiLocationResponseDto.getResults()));
        } while (apiLocationResponseDto.getInfo().getNext() != null);
        locationService.saveAll(locations.stream()
                .map(apiLocationMapper::toModel)
                .collect(Collectors.toList()));
    }

    private void syncMovieCharacters() {
        ApiCharactersResponseDto apiCharactersResponseDto;
        String currentUrl = charactersUrl;
        List<ApiMovieCharacterDto> movieCharacters = new ArrayList<>();
        do {
            apiCharactersResponseDto = httpClient.sendGet(currentUrl, ApiCharactersResponseDto.class);
            currentUrl = apiCharactersResponseDto.getInfo().getNext();
            movieCharacters.addAll(Arrays.asList(apiCharactersResponseDto.getResults()));
        } while (apiCharactersResponseDto.getInfo().getNext() != null);
        movieCharacterService.saveAll(movieCharacters.stream()
                .map(apiMovieCharacterMapper::toModel)
                .collect(Collectors.toList()));
    }

    private void syncEpisodes() {
        ApiEpisodeResponseDto apiEpisodeResponseDto;
        String currentUrl = episodesUrl;
        List<ApiEpisodeDto> episodes = new ArrayList<>();
        do {
            apiEpisodeResponseDto = httpClient.sendGet(currentUrl, ApiEpisodeResponseDto.class);
            currentUrl = apiEpisodeResponseDto.getInfo().getNext();
            episodes.addAll(Arrays.asList(apiEpisodeResponseDto.getResults()));
        } while (apiEpisodeResponseDto.getInfo().getNext() != null);
        episodeService.saveAll(episodes.stream()
                .map(apiEpisodeMapper::toModel)
                .collect(Collectors.toList()));
    }
}
