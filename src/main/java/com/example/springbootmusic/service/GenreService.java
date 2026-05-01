package com.example.springbootmusic.service;

import com.example.springbootmusic.exception.GenreAlreadyExistsException;
import com.example.springbootmusic.mapper.GenreMapper;
import com.example.springbootmusic.model.dto.GenreAddDTO;
import com.example.springbootmusic.model.dto.GenreDTO;
import com.example.springbootmusic.model.entity.Genre;
import com.example.springbootmusic.repository.GenreRepository;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreMapper genreMapper;

    public List<GenreDTO> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genreMapper.toDtoList(genres);
    }

    public void save(@NotNull GenreAddDTO genreAddDTO) {
        if(genreRepository.existsByName(genreAddDTO.getName())) {
            throw new GenreAlreadyExistsException("This genre already exists");
        }
        Genre genre = genreMapper.toEntity(genreAddDTO);
        genreRepository.save(genre);
    }
}
