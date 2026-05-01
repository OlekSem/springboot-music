package com.example.springbootmusic.mapper;

import com.example.springbootmusic.model.dto.GenreAddDTO;
import com.example.springbootmusic.model.dto.GenreDTO;
import com.example.springbootmusic.model.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDTO toGenreDTO(Genre genre);
    Genre toEntity(GenreAddDTO dto);
    List<GenreDTO> toDtoList(List<Genre> genres);
}
