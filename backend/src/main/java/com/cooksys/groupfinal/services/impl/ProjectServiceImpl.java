package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.mappers.ProjectMapper;
import com.cooksys.groupfinal.repositories.ProjectRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.services.ProjectService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

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
}
