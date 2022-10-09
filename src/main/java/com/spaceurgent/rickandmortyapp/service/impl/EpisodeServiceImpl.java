package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.model.Episode;
import com.spaceurgent.rickandmortyapp.repository.EpisodeRepository;
import com.spaceurgent.rickandmortyapp.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    private final EpisodeRepository episodeRepository;

    @Autowired
    public EpisodeServiceImpl(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @Override
    public void saveAll(List<Episode> episodes) {
        episodeRepository.saveAll(episodes);
    }
}
