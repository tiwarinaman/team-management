package com.techienaman.teammanagement.controller;

import com.techienaman.teammanagement.entity.Player;
import com.techienaman.teammanagement.exception.PlayerNotFoundException;
import com.techienaman.teammanagement.exception.TeamNotFoundException;
import com.techienaman.teammanagement.model.Packet;
import com.techienaman.teammanagement.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/team/{teamId}")
    public Packet<Player> createPlayer(@PathVariable("teamId") Long id, @RequestBody Player player) {
        try {
            return playerService.create(id, player);
        } catch (TeamNotFoundException ex) {
            return new Packet<>("failed", "operation failed cause: " + ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPlayerById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(playerService.findPlayer(id));
        } catch (PlayerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Packet<>("failed", "operation failed cause: " + ex.getMessage()));
        }
    }

}
