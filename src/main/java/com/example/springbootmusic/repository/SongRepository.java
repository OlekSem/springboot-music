package com.example.springbootmusic.repository;

import com.example.springbootmusic.model.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("select distinct s from Song s left join fetch s.genres")
    List<Song> findAllWithGenres();
}
