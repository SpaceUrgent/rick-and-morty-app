package com.spaceurgent.rickandmortyapp.controller;

import com.spaceurgent.rickandmortyapp.dto.mapper.LocationResponseMapper;
import com.spaceurgent.rickandmortyapp.dto.response.LocationDto;
import com.spaceurgent.rickandmortyapp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/locations")
public class LocationController {
    private LocationService locationService;
    private LocationResponseMapper locationResponseMapper;

    @Autowired
    public LocationController(LocationService locationService, LocationResponseMapper locationResponseMapper) {
        this.locationService = locationService;
        this.locationResponseMapper = locationResponseMapper;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) String search,
                         @RequestParam(defaultValue = "0") Integer page,
                         @RequestParam(defaultValue = "50") Integer count,
                         @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(sortBy).ascending());
        List<LocationDto> locations = locationService.getAllByPropertyIsLike(search, pageRequest).stream()
                .map(locationResponseMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("count", count);
        model.addAttribute("pages", locationService.countPages(search, count));
        model.addAttribute("search", search);
        model.addAttribute("locations", locations);
        return "locations.html";
    }
}
