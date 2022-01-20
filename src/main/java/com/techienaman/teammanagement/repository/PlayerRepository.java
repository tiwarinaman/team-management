package com.techienaman.teammanagement.repository;

import com.techienaman.teammanagement.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findPlayerByTeamId(Long team_id);

}
