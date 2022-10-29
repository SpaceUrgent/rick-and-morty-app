package com.spaceurgent.rickandmortyapp.repository;

import com.spaceurgent.rickandmortyapp.model.Episode;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    @Query("from Episode e where e.name like %?1%")
    Page<Episode> findAllByNameIsLike(String pattern, Pageable pageable);
    Long countAllByNameIsLike(String pattern);
//    @Modifying
//    @Query(value = "SELECT c.id, c.name, c.status, c.spieces, c.type, c.gender, c.origin_id, c.location_id, c.image\n" +
//            "FROM episodes e " +
//            "        LEFT JOIN episodes_characters ec ON e.id = ec.episode_id " +
//            "        LEFT JOIN characters c ON ec.character_id = c.id " +
//            "WHERE e.id = ?1 AND c.id >= ?2 LIMIT ?3",
//            nativeQuery = true)
//    List<MovieCharacter> findMovieCharactersByEpisodeId(Long episodeId, Integer idFrom, Integer limit);
//    @Query("select count(e.characters) from Episode e where e.id = ?1")
//    Long countMovieCharacterByEpisodeId(Long id);
}
