package com.example.matchmaker;

public class Main {
    public static void dataDistribution(Matchmaker matchmaker) throws InterruptedException {

        Player[] dummyPlayers = {
                new Player("1", "Ilya", 2500),
                new Player("2", "Alex", 1200),
                new Player("3", "Ivan", 2300),
                new Player("4", "Dmitry", 1100),
                new Player("5", "Elena", 1800),
                new Player("6", "Artem", 2400),
                new Player("7", "Kirill", 1350),
                new Player("8", "Maxim", 1900),
                new Player("9", "Anna", 2100),
                new Player("10", "Pavel", 1500),
                new Player("11", "Sergey", 1600),
                new Player("12", "Olga", 2200)
        };

        for (Player p : dummyPlayers) {
            matchmaker.addPlayerToQueue(p);
            Thread.sleep(100);
        }

    }
}