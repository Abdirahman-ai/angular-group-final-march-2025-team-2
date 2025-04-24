package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.TeamDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/team")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TeamController {
	
	private final TeamService teamService;


	@PostMapping("/{companyId}/teams")
	public TeamDto createTeam(@PathVariable Long companyId, @RequestBody TeamDto teamDto) {
		return teamService.createTeam(companyId, teamDto);
	}

	@PostMapping("/{teamId}/add-user/{userId}")
	public TeamDto addUserToTeam(@PathVariable Long teamId, @PathVariable Long userId) {
		return teamService.addUserToTeam(teamId, userId);
	}
	@PostMapping("/{teamId}/remove-user/{userId}")
	public TeamDto removeUserFromTeam(@PathVariable Long teamId, @PathVariable Long userId){
		return teamService.removeUser(teamId, userId);
	}

	@GetMapping("/{teamId}")
	public TeamDto getTeamById(@PathVariable Long teamId){
		return teamService.getTeamById(teamId);
	}

	@PutMapping("/{teamId}")
	public TeamDto updateTeam(@PathVariable Long teamId, @RequestBody TeamDto teamDto) {
		return teamService.updateTeam(teamId, teamDto);
	}

	@DeleteMapping("/{teamId}")
	public void deleteTeam(@PathVariable Long teamId) {
		teamService.deleteTeam(teamId);
	}


}
