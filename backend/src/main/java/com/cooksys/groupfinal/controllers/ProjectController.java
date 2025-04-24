package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.ProjectDto;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.ProjectService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProjectController {
	
	private final ProjectService projectService;

	@GetMapping("/team/{teamID}")
	public Set<ProjectDto> getAllProjects(@PathVariable long teamID){
		return projectService.getAllProjects(teamID);
	}

	@PostMapping("/team/{teamId}")
	@CrossOrigin(origins = "*")
	public ProjectDto createProject(@PathVariable Long teamId, @RequestBody ProjectDto projectDto) {
		return projectService.createProject(teamId, projectDto);
	}

	@PutMapping("/{projectID}")
	@CrossOrigin(origins = "*")
	public ProjectDto updateProject(@PathVariable long projectID, @RequestBody ProjectDto projectDto) {
		return projectService.updateProject(projectID, projectDto);
	}

}
