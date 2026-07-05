package com.example.matchmaker;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class Matchmaker implements Runnable {

    private final LinkedBlockingQueue<Player> queue = new LinkedBlockingQueue<>();
    private final int TEAM_SIZE = 5;
    private final int MATCH_SIZE = TEAM_SIZE * 2;
    private boolean running = true;
    private Thread workerThread;

    public void addPlayerToQueue(Player player) {
        queue.add(player);
        System.out.println("[Очередь] Игрок " + player.Nickname + " встал в поиск. Всего в очереди: " + queue.size());
    }

    @Override
    public void run()
    {
        this.workerThread = Thread.currentThread();
        System.out.println("[Матчмейкер] Сервер подбора запущен...");

        List<Player> currentPool = new ArrayList<>();

        while (running) {
            try {

                while (currentPool.size() < MATCH_SIZE) {
                    Player player = queue.take();
                    currentPool.add(player);
                }

                matchmake(new ArrayList<>(currentPool));
                currentPool.clear();

            } catch (InterruptedException e) {
                System.out.println("[Матчмейкер] Поток подбора был прерван во время ожидания.");

                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void matchmake(List<Player> pool) {
        System.out.println("\n[Матчмейкер] Найдено 10 игроков! Формируем балансный матч...");

        pool.sort(Comparator.comparingInt(Player::getRating).reversed());

        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();
        int sumRating1 = 0;
        int sumRating2 = 0;

        for (Player player : pool) {
            if (team1.size() < TEAM_SIZE && (team2.size() >= TEAM_SIZE || sumRating1 <= sumRating2)) {
                team1.add(player);
                sumRating1 += player.getRating();
            } else {
                team2.add(player);
                sumRating2 += player.getRating();
            }
        }

        System.out.println("==================================================");
        System.out.println("МАТЧ СФОРМИРОВАН!");
        System.out.println("Команда 1 (Средний Rating: " + (sumRating1 / TEAM_SIZE) + "): " + team1);
        System.out.println("Команда 2 (Средний Rating: " + (sumRating2 / TEAM_SIZE) + "): " + team2);
        System.out.println("Разница в суммарном рейтинге: " + Math.abs(sumRating1 - sumRating2));
        System.out.println("==================================================\n");
    }

    public void stop() {

        this.running = false;

        if (workerThread != null) {
            workerThread.interrupt();
        }
    }
}