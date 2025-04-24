package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.ProjectDto;

import java.util.List;
import java.util.Set;

public interface ProjectService {

    ProjectDto createProject(long teamID, ProjectDto projectDto);

    ProjectDto updateProject(long projectID, ProjectDto projectDto);

    Set<ProjectDto> getAllProjects(long teamID);

    void deleteProject(Long projectId);
}
