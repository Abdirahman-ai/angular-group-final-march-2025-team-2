package com.cooksys.groupfinal.services;

import java.util.List;
import java.util.Set;

import com.cooksys.groupfinal.dtos.*;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.dtos.*;
import org.springframework.web.bind.annotation.PathVariable;

public interface CompanyService {

	Set<FullUserDto> getAllUsers(Long id);

	Set<AnnouncementDto> getAllAnnouncements(Long id);

	Set<TeamDto> getAllTeams(Long id);

	Set<ProjectDto> getAllProjects(Long companyId, Long teamId);

	TeamDto removeUser(Long companyId, Long teamId, Long userId);

	CompanyDto createCompany(CompanyDto companyDto);

	CompanyDto addUserToCompany(Long companyId, Long userId);

	CompanyDto editCompanyInfo(Long companyId, CompanyDto companyDto);

	CompanyDto getCompanyById(long companyId);

	List<CompanyDto> getAllCompanies();
}
