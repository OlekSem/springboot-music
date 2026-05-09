package com.example.springbootmusic.controller;

import com.example.springbootmusic.model.dto.SongDTO;
import com.example.springbootmusic.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public String get(Model model) {
        List<SongDTO> songs = songService.getAllSongs();
        model.addAttribute("songs", songs);
        return "song-list";
    }
}
