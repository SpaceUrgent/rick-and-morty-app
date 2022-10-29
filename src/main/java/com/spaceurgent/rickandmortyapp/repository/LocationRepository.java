package com.spaceurgent.rickandmortyapp.repository;

import com.spaceurgent.rickandmortyapp.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findAll(Pageable pageable);
    @Query("from Location l " +
            "where l.name like %?1% " +
            "or l.type like %?1% " +
            "or l.dimension like %?1%")
    Page<Location> findAllByPropertyLike(String pattern, Pageable pageable);
    @Query("select count(l.id) from Location l " +
            "where l.name like %?1% " +
            "or l.type like %?1% " +
            "or l.dimension like %?1%")
    Long countAllByPropertyLike(String pattern);
}
