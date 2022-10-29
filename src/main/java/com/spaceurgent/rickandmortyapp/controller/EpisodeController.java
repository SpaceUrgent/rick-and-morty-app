package com.spaceurgent.rickandmortyapp.controller;

import com.spaceurgent.rickandmortyapp.dto.mapper.EpisodeMapper;
import com.spaceurgent.rickandmortyapp.dto.response.EpisodeDto;
import com.spaceurgent.rickandmortyapp.service.EpisodeService;
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
@RequestMapping("/episodes")
public class EpisodeController {
    private final EpisodeService episodeService;
    private final EpisodeMapper episodeMapper;

    @Autowired
    public EpisodeController(EpisodeService episodeService,
                             EpisodeMapper episodeMapper) {
        this.episodeService = episodeService;
        this.episodeMapper = episodeMapper;
    }

    @GetMapping
    public String getAll(Model model,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "50") Integer count,
                       @RequestParam(required = false) String search,
                       @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(sortBy).ascending());
        List<EpisodeDto> episodes = episodeService.findAllByNameLike(search, pageRequest)
                .stream()
                .map(episodeMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("count", count);
        model.addAttribute("pages", episodeService.countPages(search, count));
        model.addAttribute("search", search);
        model.addAttribute("episodes", episodes);
        return "episodes.html";
    }
}
