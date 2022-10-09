package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.dto.external.location.ApiLocationResponseDto;
import com.spaceurgent.rickandmortyapp.dto.mapper.LocationMapper;
import com.spaceurgent.rickandmortyapp.model.Location;
import com.spaceurgent.rickandmortyapp.repository.LocationRepository;
import com.spaceurgent.rickandmortyapp.service.HttpClient;
import com.spaceurgent.rickandmortyapp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final HttpClient httpClient;
    private final LocationMapper locationMapper;
    @Value("${app.locations.url}")
    private String locationsUrl;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, HttpClient httpClient, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.httpClient = httpClient;
        this.locationMapper = locationMapper;
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void saveAll(List<Location> locations) {
        locationRepository.saveAll(locations);
    }

    @Override
    public Location findById(Long id) {
        return locationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find location with id: " + id)
        );
    }

    @Override
    public void syncLocations() {
        List<ApiLocationResponseDto> responseDtos = httpClient.getResultsFromApi(locationsUrl, ApiLocationResponseDto.class);
        saveAll(responseDtos.stream()
                .map(ApiLocationResponseDto::getResults)
                .flatMap(Arrays::stream)
                .map(locationMapper::toModel)
                .collect(Collectors.toList()));
    }


}
