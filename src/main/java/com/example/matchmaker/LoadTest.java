package com.example.matchmaker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadTest {
    public static void main(String[] args) throws InterruptedException {
        String url = "http://localhost:8080/api/players/join";
        int totalRequests = 300_000;

        // Создаем пул из 200 параллельных потоков, которые будут одновременно слать запросы
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("Начинаем краш-тест: 100 000 запросов...");
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < totalRequests; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    String json = "{\"name\":\"Bot_" + index + "\",\"mmr\":" + (1000 + (index % 2000)) + "}";

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(json))
                            .build();

                    // Отправляем запрос асинхронно
                    client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                } catch (Exception e) {
                    // Если сервер начнет падать, мы увидим ошибки здесь
                    System.err.println("Ошибка при отправке: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        // Ждем завершения всех потоков (максимум 10 минут)
        while (!executor.isTerminated()) {
            Thread.sleep(500);
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Тест окончен! Время выполнения: " + (duration / 1000.0) + " сек.");
    }
}