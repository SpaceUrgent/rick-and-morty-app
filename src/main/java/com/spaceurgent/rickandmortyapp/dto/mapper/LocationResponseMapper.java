package com.spaceurgent.rickandmortyapp.dto.mapper;

import com.spaceurgent.rickandmortyapp.dto.response.LocationDto;
import com.spaceurgent.rickandmortyapp.model.Location;
import com.spaceurgent.rickandmortyapp.service.MovieCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationResponseMapper implements ResponseMapper<LocationDto, Location> {
    private final MovieCharacterService movieCharacterService;

    @Autowired
    public LocationResponseMapper(MovieCharacterService movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    @Override
    public LocationDto toDto(Location location) {
        LocationDto dto = new LocationDto();
        dto.setId(location.getId());
        dto.setName(location.getName());
        dto.setType(location.getType());
        dto.setDimension(location.getDimension());
        dto.setOrigins(movieCharacterService.countByOrigin(location.getId()));
        dto.setResidents(movieCharacterService.countByResidence(location.getId()));
        return dto;
    }
}
