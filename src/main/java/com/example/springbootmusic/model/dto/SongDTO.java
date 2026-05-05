package com.example.springbootmusic.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SongDTO {
    private Long id;
    private String name;
    private String fileName;
    private String artist;
    private String album;
    private Long duration_s;

    private List<GenreDTO> genres;
}
