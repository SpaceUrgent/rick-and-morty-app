package com.spaceurgent.rickandmortyapp.service;

import com.spaceurgent.rickandmortyapp.model.Episode;

import java.util.List;

public interface EpisodeService {
    void saveAll(List<Episode> episodes);
}
