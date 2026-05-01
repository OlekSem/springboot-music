package com.example.springbootmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootMusicApplication {

    public static void main(String[] args) {
        System.out.println(java.util.TimeZone.getDefault());
        SpringApplication.run(SpringbootMusicApplication.class, args);
    }

}
