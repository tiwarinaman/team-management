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

import java.util.List;
import java.util.Objects;

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
                .orElseThrow(() -> new TeamNotFoundException(id));
        player.setTeam(team);
        playerRepository.save(player);
        return new Packet<>("success", "successfully saved", player);
    }

    @Transactional
    public Packet<Player> findPlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        return new Packet<>("success", "successfully retrieve", player);
    }

    @Transactional
    public Packet<Player> updatePlayer(Long id, Player player) {
        Player playerToUpdate = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        if (player.getName() != null && player.getName().length() > 0 &&
                !Objects.equals(player.getName(), playerToUpdate.getName())) {
            playerToUpdate.setName(player.getName());
        }

        if (player.getAge() != null && !Objects.equals(player.getAge(), playerToUpdate.getAge())) {
            playerToUpdate.setAge(player.getAge());
        }

        playerRepository.save(playerToUpdate);

        return new Packet<>("success", "successfully updated", playerToUpdate);
    }

    @Transactional
    public Packet<Player> deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        playerRepository.delete(player);

        return new Packet<>("success", "successfully deleted", player);
    }

    public Packet<List<Player>> retrieveAll() {
        List<Player> players = playerRepository.findAll();
        return new Packet<>("success", "successfully retrieve", players);
    }

    public Packet<List<Player>> retrievePlayersByTeamId(Long teamId) {
        List<Player> players = playerRepository.findPlayerByTeamId(teamId);
        return new Packet<>("success", "successfully retrieve", players);
    }

}
