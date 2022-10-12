package com.spaceurgent.rickandmortyapp.controller;

import com.spaceurgent.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.spaceurgent.rickandmortyapp.dto.response.MovieCharacterDto;
import com.spaceurgent.rickandmortyapp.service.MovieCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper movieCharacterMapper;

    @Autowired
    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper movieCharacterMapper) {
        this.movieCharacterService = movieCharacterService;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(defaultValue = "0") Integer page,
                         @RequestParam(defaultValue = "20") Integer count) {
        PageRequest pageRequest = PageRequest.of(page, count);
        List<MovieCharacterDto> characterDtos = movieCharacterService.getAll(pageRequest).stream()
                .map(movieCharacterMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("characters", characterDtos);
        return "character.html";
    }
}
