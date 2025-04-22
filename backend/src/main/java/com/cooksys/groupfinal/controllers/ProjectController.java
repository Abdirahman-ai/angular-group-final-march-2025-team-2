package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.ProjectDto;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
	
	private final ProjectService projectService;

	@PostMapping("/team/{teamId}")
	@CrossOrigin(origins = "*")
	public ProjectDto createProject(@PathVariable Long teamId, @RequestBody ProjectDto projectDto) {
		return projectService.createProject(teamId, projectDto);
	}

}
