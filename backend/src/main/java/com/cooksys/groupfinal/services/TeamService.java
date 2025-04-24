package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.TeamDto;

public interface TeamService {
    public TeamDto removeUser(Long teamId, Long userId);

    TeamDto createTeam(Long companyId, TeamDto teamDto);

    TeamDto addUserToTeam(Long teamId, Long userId);

    TeamDto updateTeam(Long teamId, TeamDto teamDto);

    void deleteTeam(Long teamId);
}
