package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.dto.external.episode.ApiEpisodeResponseDto;
import com.spaceurgent.rickandmortyapp.dto.mapper.EpisodeMapper;
import com.spaceurgent.rickandmortyapp.model.Episode;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.repository.EpisodeRepository;
import com.spaceurgent.rickandmortyapp.service.EpisodeService;
import com.spaceurgent.rickandmortyapp.service.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public List<Episode> findAll(PageRequest pageRequest) {
        return episodeRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<Episode> findAllByNameLike(String name, PageRequest pageRequest) {
        if (name == null) {
            return findAll(pageRequest);
        }
        return episodeRepository.findAllByNameIsLike(name, pageRequest).getContent();
    }

    @Override
    public Long countAll() {
        return episodeRepository.count();
    }

    @Override
    public Long countAllByNameLike(String name) {
        return episodeRepository.countAllByNameIsLike(name);
    }

    @Override
    public Long countPages(String name, Integer count) {
        if (name == null || name.isEmpty()) {
            return countAll() / count;
        }
        return countAllByNameLike(name) / count;
    }

    @Override
    public Episode findById(Long id) {
        return episodeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find episode by id: " + id)
        );
    }

//    @Override
//    public Long countMovieCharactersPagesByEpisodeId(Long id, Integer count) {
//        return episodeRepository.countMovieCharacterByEpisodeId(id) / count;
//    }
//
//    @Override
//    public List<MovieCharacter> findMovieCharactersByEpisodeId(Long episodeId, Integer page, Integer count) {
//        if (episodeId == null) {
//            throw new RuntimeException("Episode Id can't be null");
//        }
//        int from = (page == 0) ? 1 : (page * count + 1);
//        return episodeRepository.findMovieCharactersByEpisodeId(episodeId, from, count);
//    }
}
