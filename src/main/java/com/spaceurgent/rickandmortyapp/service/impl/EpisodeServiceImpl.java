package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.dto.external.episode.ApiEpisodeResponseDto;
import com.spaceurgent.rickandmortyapp.dto.mapper.EpisodeMapper;
import com.spaceurgent.rickandmortyapp.model.Episode;
import com.spaceurgent.rickandmortyapp.repository.EpisodeRepository;
import com.spaceurgent.rickandmortyapp.service.EpisodeService;
import com.spaceurgent.rickandmortyapp.service.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final HttpClient httpClient;
    private final EpisodeMapper episodeMapper;
    @Value("${app.episodes.url}")
    private String episodesUrl;

    @Autowired
    public EpisodeServiceImpl(EpisodeRepository episodeRepository, HttpClient httpClient, EpisodeMapper episodeMapper) {
        this.episodeRepository = episodeRepository;
        this.httpClient = httpClient;
        this.episodeMapper = episodeMapper;
    }

    @Override
    public void saveAll(List<Episode> episodes) {
        episodeRepository.saveAll(episodes);
    }

    @Override
    public void syncEpisodes() {
        List<ApiEpisodeResponseDto> responseDtos = httpClient.getResultsFromApi(episodesUrl, ApiEpisodeResponseDto.class);
        saveAll(responseDtos.stream()
                .map(ApiEpisodeResponseDto::getResults)
                .flatMap(Arrays::stream)
                .map(episodeMapper::toModel)
                .collect(Collectors.toList()));
    }
}
