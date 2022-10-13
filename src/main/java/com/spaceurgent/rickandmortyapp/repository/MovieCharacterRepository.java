package com.spaceurgent.rickandmortyapp.repository;

import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {

    Page<MovieCharacter> findAll(Pageable pageable);

    @Query("from MovieCharacter mc " +
            "join mc.location l " +
            "join mc.origin o " +
            "where mc.name like %?1% or l.name like %?1% or o.name like %?1%")
    Page<MovieCharacter> findAllByNameOrLocation(String value, Pageable pageable);
}
