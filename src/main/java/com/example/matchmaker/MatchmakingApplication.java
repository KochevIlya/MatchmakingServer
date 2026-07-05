package com.example.matchmaker;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MatchmakingApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(MatchmakingApplication.class, args);
    }


    @Bean
    public CommandLineRunner testRunning(Matchmaker matchmaker)
    {
        return args -> {
            new Thread(matchmaker).start();
            Main.dataDistribution(matchmaker);

        };



    }

}
