package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.TeamMapper;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.services.TeamService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserRepository userRepository;
    @Override
    public TeamDto removeUser(Long teamId, Long userId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) {
            throw new NotFoundException("A team with the provided id does not exist.");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new NotFoundException("A user with the provided id does not exist");
        }

        Team team = teamOptional.get();
        User user = userOptional.get();

        Set<User> teammates = team.getTeammates();
        if(!teammates.contains(user)){
            throw new NotFoundException("The user is not a member of this team");
        }

        teammates.remove(user);
        team.setTeammates(teammates);

        Team savedTeam = teamRepository.save(team);
        return teamMapper.entityToDto(savedTeam);

    }
}
