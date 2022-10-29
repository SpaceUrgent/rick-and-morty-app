package com.spaceurgent.rickandmortyapp.repository;

import com.spaceurgent.rickandmortyapp.model.Location;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {

    Page<MovieCharacter> findAll(Pageable pageable);

    Page<MovieCharacter> findAllByNameContains(String value, Pageable pageable);

    Page<MovieCharacter> findAllByStatus(Status status, Pageable pageable);

    Page<MovieCharacter> findAllByStatusAndNameContains(Status status, String value, Pageable pageable);

    Long countAllByNameContains(String namePattern);
    Long countAllByStatusAndNameContains(Status status, String namePattern);
    Long countAllByLocation(Location location);
    Long countAllByOrigin(Location origin);
    Page<MovieCharacter> findAllByOrigin(Location origin, Pageable pageable);
    Page<MovieCharacter> findAllByLocation(Location residence, Pageable pageable);

    @Modifying
    @Query(value = "SELECT c.id, c.name, c.status, c.spieces, c.type, c.gender, c.origin_id, c.location_id, c.image\n" +
            "FROM episodes e " +
            "        LEFT JOIN episodes_characters ec ON e.id = ec.episode_id " +
            "        LEFT JOIN characters c ON ec.character_id = c.id " +
            "WHERE e.id = ?1 LIMIT ?2, ?3",
            nativeQuery = true)
    List<MovieCharacter> findMovieCharactersByEpisodeId(Long episodeId, Integer limitStart, Integer limitEnd);
    @Query(value = "SELECT count(c.id) " +
            "FROM episodes e " +
            "        LEFT JOIN episodes_characters ec ON e.id = ec.episode_id " +
            "        LEFT JOIN characters c ON ec.character_id = c.id " +
            "WHERE e.id = ?1",
            nativeQuery = true)
    Long countMovieCharacterByEpisodeId(Long id);
}
