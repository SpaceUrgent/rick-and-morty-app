package com.spaceurgent.rickandmortyapp.service;

import com.spaceurgent.rickandmortyapp.model.Location;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface LocationService {
    Location save(Location location);

    void saveAll(List<Location> locations);

    Location findById(Long id);

    void syncLocations();
    List<Location> getAll(PageRequest pageRequest);
    List<Location> getAllByPropertyIsLike(String pattern, PageRequest pageRequest);
    Long countPages(String pattern, Integer count);
    Long countPages(Integer count);
}
