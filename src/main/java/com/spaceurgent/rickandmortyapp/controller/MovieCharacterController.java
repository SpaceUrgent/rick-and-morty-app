package com.spaceurgent.rickandmortyapp.controller;

import com.spaceurgent.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.spaceurgent.rickandmortyapp.dto.response.MovieCharacterDto;
import com.spaceurgent.rickandmortyapp.service.EpisodeService;
import com.spaceurgent.rickandmortyapp.service.MovieCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                         @RequestParam(required = false) String name,
                         @RequestParam(defaultValue = "all") String status,
                         @RequestParam(defaultValue = "0") Integer page,
                         @RequestParam(defaultValue = "20") Integer count) {
        PageRequest pageRequest = PageRequest.of(page, count);
        List<MovieCharacterDto> characterDtos = movieCharacterService
                .findAllByNameContainsAndStatus(status, name, pageRequest)
                .stream()
                .map(movieCharacterMapper::toDto)
                .collect(Collectors.toList());
        Long pages = movieCharacterService.countPages(count, status, name);
        model.addAttribute("status", status);
        model.addAttribute("name", name);
        model.addAttribute("characters", characterDtos);
        model.addAttribute("currentPage", page);
        model.addAttribute("count", count);
        model.addAttribute("pages", pages);
        return "character.html";
    }

    @GetMapping("/residence/{id}")
    public String getAllByResidence(Model model,
                                    @PathVariable Long id,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "20") Integer count) {
        PageRequest pageRequest = PageRequest.of(page, count);
        List<MovieCharacterDto> characterDtos = movieCharacterService.findAllByResidence(Long.valueOf(id), pageRequest).stream()
                .map(movieCharacterMapper::toDto)
                .collect(Collectors.toList());
        Long pages = movieCharacterService.countPagesByResidence(count, id);
        model.addAttribute("id", id);
        model.addAttribute("status", "");
        model.addAttribute("name", "");
        model.addAttribute("characters", characterDtos);
        model.addAttribute("currentPage", page);
        model.addAttribute("count", count);
        model.addAttribute("pages", pages);
        return "character-residence.html";
    }
    @GetMapping("/origin/{id}")
    public String getAllByOrigin(Model model,
                                    @PathVariable Long id,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "20") Integer count) {
        PageRequest pageRequest = PageRequest.of(page, count);
        List<MovieCharacterDto> characterDtos = movieCharacterService.findAllByOrigin(Long.valueOf(id), pageRequest)
                .stream()
                .map(movieCharacterMapper::toDto)
                .collect(Collectors.toList());
        Long pages = movieCharacterService.countPagesByOrigin(count, id);
        model.addAttribute("id", id);
        model.addAttribute("status", "");
        model.addAttribute("name", "");
        model.addAttribute("characters", characterDtos);
        model.addAttribute("currentPage", page);
        model.addAttribute("count", count);
        model.addAttribute("pages", pages);
        return "character-origin.html";
    }

    @GetMapping("/episode/{id}")
    public String getAllByEpisode(Model model,
                                  @PathVariable Long id,
                                  @RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "20") Integer count) {
        List<MovieCharacterDto> characterDtos = movieCharacterService.findMovieCharactersByEpisodeId(id, page, count)
                .stream()
                .map(movieCharacterMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("id", id);
        model.addAttribute("status", "");
        model.addAttribute("name", "");
        model.addAttribute("characters", characterDtos);
        model.addAttribute("currentPage", page);
        model.addAttribute("count", count);
        model.addAttribute("pages",
                movieCharacterService.countMovieCharactersPagesByEpisodeId(id, count));
        return "character-episode.html";
    }
}
