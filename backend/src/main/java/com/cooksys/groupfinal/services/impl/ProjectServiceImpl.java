package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.ProjectMapper;
import com.cooksys.groupfinal.repositories.ProjectRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.services.ProjectService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto createProject(long teamID, ProjectDto projectDto) {
        Optional<Team> teamOpt = teamRepository.findById(teamID);
        if(teamOpt.isEmpty()){
            throw new BadRequestException("Team with ID: " + teamID + " does not exist!");
        }
        Project newProject = new Project();
        newProject.setName(projectDto.getName());
        newProject.setDescription(projectDto.getDescription());
        newProject.setTeam(teamOpt.get());
        newProject.setActive(true);

        projectRepository.saveAndFlush(newProject);

        return projectMapper.entityToDto(newProject);
    }

    @Override
    public ProjectDto updateProject(long projectID, ProjectDto projectDto) {
        Optional<Project> projectOpt = projectRepository.findById(projectID);
        if (projectOpt.isEmpty()) {
            throw new BadRequestException("Project with project ID: " + projectID + " does not exist!");
        }

        Project project = projectOpt.get();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setActive(projectDto.isActive());

        projectRepository.saveAndFlush(project);

        return projectMapper.entityToDto(project);
    }

    @Override
    public Set<ProjectDto> getAllProjects(long teamID) {
        Optional<Team> teamOpt = teamRepository.findById(teamID);
        if(teamOpt.isEmpty()){
            throw new BadRequestException("Team with Team ID: " + teamID + " Does not exist!");
        }

        Set<Project> projects = teamOpt.get().getProjects();

        return projectMapper.entitiesToDtos((projects));
    }

    @Override
    public void deleteProject(Long projectId) {
        if(!projectRepository.existsById(projectId)){
            throw new NotFoundException("Project not found");
        }
        projectRepository.deleteById(projectId);
    }
}
