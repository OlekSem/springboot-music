package com.example.springbootmusic.service;

import com.example.springbootmusic.mapper.SongMapper;
import com.example.springbootmusic.model.dto.SongDTO;
import com.example.springbootmusic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private SongMapper songMapper;

    public List<SongDTO> getAllSongs() {

        return songMapper.toListDto(songRepository.findAllWithGenres());
    }
}
