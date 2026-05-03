package com.example.colonydrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 스케줄러 위해서 넣음
public class ColonyDropApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColonyDropApplication.class, args);
    }

}
