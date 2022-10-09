package com.spaceurgent.rickandmortyapp.service.impl;

import com.spaceurgent.rickandmortyapp.model.Location;
import com.spaceurgent.rickandmortyapp.repository.LocationRepository;
import com.spaceurgent.rickandmortyapp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
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


}
