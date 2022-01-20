package com.techienaman.teammanagement.controller;

import com.techienaman.teammanagement.entity.Team;
import com.techienaman.teammanagement.exception.DuplicateTeamNameException;
import com.techienaman.teammanagement.exception.TeamNotFoundException;
import com.techienaman.teammanagement.model.Packet;
import com.techienaman.teammanagement.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/team")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public Packet<Team> createTeam(@RequestBody Team team) {
        try {
            return teamService.create(team);
        } catch (DuplicateTeamNameException ex) {
            return new Packet<>("failed", "operation failed cause: " + ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveTeam(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(teamService.findTeamById(id));
        } catch (TeamNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Packet<>("failed", ex.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeamDetails(@PathVariable("id") Long id, @RequestBody Team team) {
        try {
            return ResponseEntity.ok(teamService.updateTeam(id, team));
        } catch (TeamNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Packet<>("failed", ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(teamService.deleteTeam(id));
        } catch (TeamNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Packet<>("result", ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTeams() {
        return ResponseEntity.ok(teamService.retrieveAll());
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<?> findTeamByPlayerId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(teamService.retrieveTeamByPlayerId(id));
    }

}
