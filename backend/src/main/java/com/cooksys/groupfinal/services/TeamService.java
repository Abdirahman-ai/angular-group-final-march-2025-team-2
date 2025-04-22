package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.TeamDto;

public interface TeamService {
    public TeamDto removeUser(Long teamId, Long userId);

}
