package com.spaceurgent.rickandmortyapp.dto.mapper;

import com.spaceurgent.rickandmortyapp.dto.external.charcter.ApiMovieCharacterDto;
import com.spaceurgent.rickandmortyapp.dto.external.charcter.ApiOriginDto;
import com.spaceurgent.rickandmortyapp.model.Location;
import com.spaceurgent.rickandmortyapp.model.MovieCharacter;
import com.spaceurgent.rickandmortyapp.model.enums.Gender;
import com.spaceurgent.rickandmortyapp.model.enums.Status;
import com.spaceurgent.rickandmortyapp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

@Component
public class ApiMovieCharacterMapper implements RequestMapper<MovieCharacter, ApiMovieCharacterDto> {
    private final LocationService locationService;

    @Autowired
    public ApiMovieCharacterMapper(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public MovieCharacter toModel(ApiMovieCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setId(dto.getId());
        movieCharacter.setName(dto.getName());
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setSpieces(dto.getSpecies());
        movieCharacter.setType(dto.getType());
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setOrigin(getLocation(dto.getOrigin()));
        movieCharacter.setLocation(getLocation(dto.getLocation()));
        movieCharacter.setImage(downloadImage(dto.getImage()));
        return movieCharacter;
    }

    private Location getLocation(ApiOriginDto dto) {
        if (dto.getName().equals("unknown")) {
            return null;
        }
        String[] locationUrl = dto.getUrl().split("/");
        return locationService.findById(Long.valueOf(locationUrl[locationUrl.length - 1]));
    }

    private byte[] downloadImage(String url) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream())) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Can't download an image from: " + url);
        }
    }
}
