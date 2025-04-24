package com.cooksys.groupfinal.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.dtos.CompanyDto;
import com.cooksys.groupfinal.services.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {
	
	private final CompanyService companyService;
	
	@GetMapping("/{id}/users")
    public Set<FullUserDto> getAllUsers(@PathVariable Long id) {
        return companyService.getAllUsers(id);
    }
	
	@GetMapping("/{id}/announcements")
    public Set<AnnouncementDto> getAllAnnouncements(@PathVariable Long id) {
        return companyService.getAllAnnouncements(id);
    }
	
	@GetMapping("/{id}/teams")
    public Set<TeamDto> getAllTeams(@PathVariable Long id) {
        return companyService.getAllTeams(id);
    }
	
	@GetMapping("/{companyId}/teams/{teamId}/projects") 
	public Set<ProjectDto> getAllProjects(@PathVariable Long companyId, @PathVariable Long teamId) {
		return companyService.getAllProjects(companyId, teamId);
	}

    @PostMapping// admin only
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        return companyService.createCompany(companyDto);
    }

    @PostMapping("/{companyId}/add-user/{userId}")
    public CompanyDto addUserToCompany(@PathVariable Long companyId, @PathVariable Long userId) {
        return companyService.addUserToCompany(companyId, userId);
    }

    @PutMapping("/{companyId}")
    public CompanyDto editCompanyInfo(@PathVariable Long companyId, @RequestBody CompanyDto companyDto){
        return companyService.editCompanyInfo(companyId, companyDto);
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/{companyId}")
    public CompanyDto getCompanyById(@PathVariable long companyId){
        return companyService.getCompanyById(companyId);
    }
    @PostMapping("/{companyId}/team/{teamId}/remove-user/{userId}")
    public TeamDto removeUser(@PathVariable Long companyId, @PathVariable Long teamId, @PathVariable Long userId){
        return companyService.removeUser(companyId, teamId, userId);
    }

}
