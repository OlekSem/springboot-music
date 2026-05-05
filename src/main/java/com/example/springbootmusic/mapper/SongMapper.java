package com.example.springbootmusic.mapper;

import com.example.springbootmusic.model.dto.SongDTO;
import com.example.springbootmusic.model.entity.Song;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface SongMapper {
    SongDTO toDto(Song song);
    List<SongDTO> toListDto(List<Song> songs);
}
