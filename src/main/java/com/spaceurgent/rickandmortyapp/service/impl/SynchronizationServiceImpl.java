package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.service.EpisodeService;
import com.spaceurgent.rickandmortyapp.service.LocationService;
import com.spaceurgent.rickandmortyapp.service.MovieCharacterService;
import com.spaceurgent.rickandmortyapp.service.SynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SynchronizationServiceImpl implements SynchronizationService {
    private final LocationService locationService;
    private final MovieCharacterService movieCharacterService;
    private final EpisodeService episodeService;

    @Autowired
    public SynchronizationServiceImpl(LocationService locationService,
                                      MovieCharacterService movieCharacterService,
                                      EpisodeService episodeService) {
        this.locationService = locationService;
        this.movieCharacterService = movieCharacterService;
        this.episodeService = episodeService;
    }

    @Scheduled(cron = "0 0 23 * * ?")
    @Override
    public void syncData() {
        locationService.syncLocations();
        movieCharacterService.syncMovieCharacters();
        episodeService.syncEpisodes();
    }
}
