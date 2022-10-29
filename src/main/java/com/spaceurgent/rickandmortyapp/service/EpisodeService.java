package com.spaceurgent.rickandmortyapp.service;

import com.spaceurgent.rickandmortyapp.model.Episode;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface EpisodeService {
    void saveAll(List<Episode> episodes);

    void syncEpisodes();

    List<Episode> findAll(PageRequest pageRequest);
    List<Episode> findAllByNameLike(String name, PageRequest pageRequest);
    Long countAll();
    Long countAllByNameLike(String name);
    Long countPages(String name, Integer count);
    Episode findById(Long id);
//    Long countMovieCharactersPagesByEpisodeId(Long id, Integer count);
//    List<MovieCharacter> findMovieCharactersByEpisodeId(Long episodeId, Integer page, Integer count);
}
