package com.techienaman.teammanagement.service;

import com.techienaman.teammanagement.entity.Player;
import com.techienaman.teammanagement.entity.Team;
import com.techienaman.teammanagement.exception.PlayerNotFoundException;
import com.techienaman.teammanagement.exception.TeamNotFoundException;
import com.techienaman.teammanagement.model.Packet;
import com.techienaman.teammanagement.repository.PlayerRepository;
import com.techienaman.teammanagement.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    public PlayerService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public Packet<Player> create(Long id, Player player) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team Not found"));
        player.setTeam(team);
        playerRepository.save(player);
        return new Packet<>("success", "successfully saved", player);
    }

    public Packet<Player> findPlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        return new Packet<>("success", "successfully retrieve", player);
    }

}
