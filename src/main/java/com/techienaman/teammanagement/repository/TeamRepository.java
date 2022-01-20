package com.techienaman.teammanagement.repository;

import com.techienaman.teammanagement.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findTeamByName(String name);

    List<Team> findTeamByPlayersId(Long playerId);
}
