package com.spaceurgent.rickandmortyapp.dto.mapper;

import com.spaceurgent.rickandmortyapp.dto.external.charcter.ApiMovieCharacterDto;
import com.spaceurgent.rickandmortyapp.dto.external.charcter.ApiOriginDto;
import com.spaceurgent.rickandmortyapp.dto.response.MovieCharacterDto;
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
public class MovieCharacterMapper implements RequestMapper<MovieCharacter, ApiMovieCharacterDto>,
        ResponseMapper<MovieCharacterDto, MovieCharacter> {
    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
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

    @Override
    public MovieCharacterDto toDto(MovieCharacter movieCharacter) {
        MovieCharacterDto dto = new MovieCharacterDto();
        dto.setId(movieCharacter.getId());
        dto.setName(movieCharacter.getName());
        dto.setStatus(movieCharacter.getStatus().getValue());
        dto.setSpieces(movieCharacter.getSpieces());
        dto.setType(movieCharacter.getType());
        dto.setGender(movieCharacter.getGender().getValue());
        if (movieCharacter.getOrigin() == null) {
            dto.setOrigin("unknown");
        } else {
            dto.setOrigin(movieCharacter.getOrigin().getName());
        }
        if (movieCharacter.getLocation() == null) {
            dto.setResident("unknown");
        } else {
            dto.setResident(movieCharacter.getLocation().getName());
        }
        dto.setImage(movieCharacter.getImage());
        return dto;
    }
}
