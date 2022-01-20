package com.techienaman.teammanagement.controller;

import com.techienaman.teammanagement.entity.Player;
import com.techienaman.teammanagement.exception.PlayerNotFoundException;
import com.techienaman.teammanagement.exception.TeamNotFoundException;
import com.techienaman.teammanagement.model.Packet;
import com.techienaman.teammanagement.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
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
                    .body(new Packet<>("failed", ex.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlayerDetails(@PathVariable("id") Long id,
                                                 @RequestBody Player player) {
        try {
            return ResponseEntity.ok(playerService.updatePlayer(id, player));
        } catch (PlayerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Packet<>("failed", ex.getMessage()));
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(playerService.deletePlayer(id));
        } catch (PlayerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Packet<>("failed", ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllPlayers() {
        return ResponseEntity.ok(playerService.retrieveAll());
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> findPlayersByTeamId(@PathVariable("teamId") Long id) {
        return ResponseEntity.ok(playerService.retrievePlayersByTeamId(id));
    }

}
