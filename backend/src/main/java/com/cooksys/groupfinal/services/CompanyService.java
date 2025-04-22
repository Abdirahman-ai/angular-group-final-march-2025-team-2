package com.cooksys.groupfinal.services;

import java.util.Set;

import com.cooksys.groupfinal.dtos.*;
import org.springframework.web.bind.annotation.PathVariable;

public interface CompanyService {

	Set<FullUserDto> getAllUsers(Long id);

	Set<AnnouncementDto> getAllAnnouncements(Long id);

	Set<TeamDto> getAllTeams(Long id);

	Set<ProjectDto> getAllProjects(Long companyId, Long teamId);

	CompanyDto createCompany(CompanyDto companyDto);

	CompanyDto addUserToCompany(Long companyId, Long userId);

	CompanyDto editCompanyInfo(Long companyId, CompanyDto companyDto);

	CompanyDto getCompanyById(long companyId);
}
