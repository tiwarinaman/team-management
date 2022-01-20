package com.techienaman.teammanagement.service;

import com.techienaman.teammanagement.entity.Team;
import com.techienaman.teammanagement.exception.DuplicateTeamNameException;
import com.techienaman.teammanagement.model.Packet;
import com.techienaman.teammanagement.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Packet<Team> create(Team team) {

        Team teamDetail = teamRepository.findTeamByName(team.getName());

        if (teamDetail != null && teamDetail.getName().equalsIgnoreCase(team.getName())) {
            throw new DuplicateTeamNameException("Duplicate team is not allowed");
        }

        teamRepository.save(team);
        return new Packet<>("success", "successfully saved", team);
    }

}
