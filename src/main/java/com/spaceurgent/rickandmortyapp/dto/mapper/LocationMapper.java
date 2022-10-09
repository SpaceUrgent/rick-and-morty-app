package com.spaceurgent.rickandmortyapp.dto.mapper;

import com.spaceurgent.rickandmortyapp.dto.external.location.ApiLocationDto;
import com.spaceurgent.rickandmortyapp.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper implements RequestMapper<Location, ApiLocationDto> {
    @Override
    public Location toModel(ApiLocationDto dto) {
        Location location = new Location();
        location.setId(dto.getId());
        location.setName(dto.getName());
        location.setType(dto.getType());
        location.setDimension(dto.getDimension());
        return location;
    }
}
