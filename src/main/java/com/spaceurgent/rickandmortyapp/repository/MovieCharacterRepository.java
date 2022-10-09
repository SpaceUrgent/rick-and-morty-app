package com.spaceurgent.rickandmortyapp.repository;

import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
}
