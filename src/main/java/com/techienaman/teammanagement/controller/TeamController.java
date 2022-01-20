package com.techienaman.teammanagement.controller;

import com.techienaman.teammanagement.entity.Team;
import com.techienaman.teammanagement.exception.DuplicateTeamNameException;
import com.techienaman.teammanagement.model.Packet;
import com.techienaman.teammanagement.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public Packet<Team> createTeam(@RequestBody Team team) {
        try {
            return teamService.create(team);
        } catch (DuplicateTeamNameException ex) {
            return new Packet<>("failed", "operation failed cause: " + ex.getMessage());
        }
    }

}
