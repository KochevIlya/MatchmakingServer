package com.example.matchmaker;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/players")
public class MatchmakingController {

    private final Matchmaker _matchmaker;
    public MatchmakingController(Matchmaker matchmaker) {
        this._matchmaker = matchmaker;
    }

    @PostMapping("/join")
    public String joinQueue(@RequestBody PlayerRequest request) {
        String uniqueId = UUID.randomUUID().toString();

        Player player = new Player(uniqueId, request.name(), request.rating());

        _matchmaker.addPlayerToQueue(player);

        return "Игрок " + player.Nickname + " (MMR: " + player.getRating() + ") успешно встал в очередь! ID: " + uniqueId;
    }



}
