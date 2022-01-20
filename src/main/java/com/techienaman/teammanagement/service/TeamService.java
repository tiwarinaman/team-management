package com.techienaman.teammanagement.service;

import com.techienaman.teammanagement.entity.Team;
import com.techienaman.teammanagement.exception.DuplicateTeamNameException;
import com.techienaman.teammanagement.exception.TeamNotFoundException;
import com.techienaman.teammanagement.model.Packet;
import com.techienaman.teammanagement.repository.TeamRepository;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
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

    @Transactional
    public Packet<Team> findTeamById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
        return new Packet<>("success", "successfully retrieve", team);
    }

    @Transactional
    public Packet<Team> updateTeam(Long id, Team team) {
        Team teamToUpdate = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        if (team.getName() != null && team.getName().length() > 0
                && !Objects.equals(team.getName(), teamToUpdate.getName())) {
            teamToUpdate.setName(team.getName());
        }

        if (team.getLocation() != null && team.getLocation().length() > 0
                && !Objects.equals(team.getLocation(), teamToUpdate.getLocation())) {
            teamToUpdate.setLocation(team.getLocation());
        }

        teamRepository.save(teamToUpdate);

        return new Packet<>("success", "successfully updated", teamToUpdate);
    }

    public Packet<Team> deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));

        teamRepository.delete(team);

        return new Packet<>("success", "successfully deleted", team);
    }

    public Packet<List<Team>> retrieveAll() {
        List<Team> teams = teamRepository.findAll();
        return new Packet<>("success", "successfully retrieve", teams);
    }

    public Packet<List<Team>> retrieveTeamByPlayerId(Long id) {
        List<Team> teams = teamRepository.findTeamByPlayersId(id);
        return new Packet<>("success", "successfully retrieve", teams);
    }

}
