package com.example.springbootmusic.controller;

import com.example.springbootmusic.exception.GenreAlreadyExistsException;
import com.example.springbootmusic.model.dto.GenreAddDTO;
import com.example.springbootmusic.model.dto.GenreDTO;
import com.example.springbootmusic.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/genres")
public class GenresController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public String listGenres(Model model) {
        List<GenreDTO> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genre-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("genre", new GenreAddDTO());
        return "genre-add";
    }

    @PostMapping("/add")
    public String saveGenre(@ModelAttribute("genre") GenreAddDTO genreAddDTO, Model model) {
        try {
            genreService.save(genreAddDTO);
            return "redirect:/genres";
        } catch (GenreAlreadyExistsException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("genre", genreAddDTO);
            return "genre-add";
        }
    }
}
