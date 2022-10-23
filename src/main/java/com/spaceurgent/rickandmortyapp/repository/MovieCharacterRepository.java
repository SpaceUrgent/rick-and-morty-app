package com.spaceurgent.rickandmortyapp.repository;

import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {

    Page<MovieCharacter> findAll(Pageable pageable);

    Page<MovieCharacter> findAllByNameContains(String value, Pageable pageable);

    Page<MovieCharacter> findAllByStatus(Status status, Pageable pageable);

    Page<MovieCharacter> findAllByStatusAndNameContains(Status status, String value, Pageable pageable);

    Long countAllByNameContains(String namePattern);
    Long countAllByStatusAndNameContains(Status status, String namePattern);
}
