package com.spaceurgent.rickandmortyapp.service;

import com.spaceurgent.rickandmortyapp.model.Location;

import java.util.List;

public interface LocationService {
    Location save(Location location);

    void saveAll(List<Location> locations);

    Location findById(Long id);
}
