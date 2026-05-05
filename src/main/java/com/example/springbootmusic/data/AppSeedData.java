package com.example.springbootmusic.data;

import com.example.springbootmusic.model.entity.Genre;
import com.example.springbootmusic.model.entity.Song;
import com.example.springbootmusic.repository.GenreRepository;
import com.example.springbootmusic.repository.SongRepository;
import com.github.javafaker.Faker;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AppSeedData {
    @Value( "${upload.path}")
    private String uploadPath;

    private final GenreRepository genreRepository;
    private final SongRepository songRepository;
    private final Faker faker = new Faker();
    //Цей метод буде Seed даних у БД
    //Цей метод в java Spring буде зпускати автоматично
    @PostConstruct
    public void seed() throws IOException{
        seedGenres();
        seedSongs();
    }



    private void seedSongs() throws IOException {
        if (songRepository.count() > 0) return;
        System.out.println("---------Seed songs-----------");

        var paths = Paths.get(uploadPath);

        var genres = genreRepository.findAll();
        Random random = new Random();


        Files.list(paths)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    Mp3File mp3file = null;
                    try {
                        mp3file = new Mp3File(file);
                        if (mp3file.hasId3v2Tag()) {
                            ID3v1 id3v2Tag = mp3file.getId3v2Tag();
//                            System.out.println("Track: " + id3v2Tag.getTrack());
                            System.out.println("Artist: " + id3v2Tag.getArtist());
                            System.out.println("Title: " + id3v2Tag.getTitle());
                            System.out.println("Album: " + id3v2Tag.getAlbum());
                            System.out.println("Year: " + id3v2Tag.getYear());
//                            System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
//                            System.out.println("Comment: " + id3v2Tag.getComment());
                            System.out.println("--------------");

                            Song song = new Song();
                            song.setName(id3v2Tag.getTitle());
                            song.setArtist(id3v2Tag.getArtist());
                            song.setDuration_s(mp3file.getLengthInSeconds());
                            song.setAlbum(id3v2Tag.getAlbum());
                            song.setFileName(file.getFileName().toString());

                            Collections.shuffle(genres);
                            List<Genre> randomGenres = genres.stream().limit(random.nextInt(3)).toList();
                            song.setGenres(randomGenres);

                            songRepository.save(song);
                        }
                    } catch (Exception e) {

                    }
                });
    }

    private void seedGenres() {
        if (genreRepository.count() > 0) return;
        System.out.println("---------Seed genres-----------");
        String musicGenre = faker.music().genre();
        System.out.println("Music Genre: " + musicGenre);

        String[] list = {"acoustic",
                "afrobeat",
                "alt-rock",
                "alternative",
                "ambient",
                "anime",
                "black-metal",
                "bluegrass",
                "blues",
                "bossanova",
                "brazil",
                "breakbeat",
                "british",
                "cantopop",
                "chicago-house",
                "children",
                "chill",
                "classical",
                "club",
                "comedy",
                "country",
                "dance",
                "dancehall",
                "death-metal",
                "deep-house",
                "detroit-techno",
                "disco",
                "disney",
                "drum-and-bass",
                "dub",
                "dubstep",
                "edm",
                "electro",
                "electronic",
                "emo",
                "folk",
                "forro",
                "french",
                "funk",
                "garage",
                "german",
                "gospel",
                "goth",
                "grindcore",
                "groove",
                "grunge",
                "guitar",
                "happy",
                "hard-rock",
                "hardcore",
                "hardstyle",
                "heavy-metal",
                "hip-hop",
                "holidays",
                "honky-tonk",
                "house",
                "idm",
                "indian",
                "indie",
                "indie-pop",
                "industrial",
                "iranian",
                "j-dance",
                "j-idol",
                "j-pop",
                "j-rock",
                "jazz",
                "k-pop",
                "kids",
                "latin",
                "latino",
                "malay",
                "mandopop",
                "metal",
                "metal-misc",
                "metalcore",
                "minimal-techno",
                "movies",
                "mpb",
                "new-age",
                "new-release",
                "opera",
                "pagode",
                "party",
                "philippines-opm",
                "piano",
                "pop",
                "pop-film",
                "post-dubstep",
                "power-pop",
                "progressive-house",
                "psych-rock",
                "punk",
                "punk-rock",
                "r-n-b",
                "rainy-day",
                "reggae",
                "reggaeton",
                "road-trip",
                "rock",
                "rock-n-roll",
                "rockabilly",
                "romance",
                "sad",
                "salsa",
                "samba",
                "sertanejo",
                "show-tunes",
                "singer-songwriter",
                "ska",
                "sleep",
                "songwriter",
                "soul",
                "soundtracks",
                "spanish",
                "study",
                "summer",
                "swedish",
                "synth-pop",
                "tango",
                "techno",
                "trance",
                "trip-hop",
                "turkish",
                "work-out"};
        List<Genre> genreList = new ArrayList<>();
        for(String l: list) {
            String shortDesc = faker.lorem().sentence();
            Genre genre = new Genre(l, shortDesc);
            genreList.add(genre);
        }
        genreRepository.saveAll(genreList);
    }
}