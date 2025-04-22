package com.cooksys.groupfinal.controllers;

import java.util.List;
import java.util.Set;

import com.cooksys.groupfinal.dtos.*;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
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

    @PostMapping
    @CrossOrigin(origins = "*")  // admin only
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        return companyService.createCompany(companyDto);
    }

    @PostMapping("/{companyId}/add-user/{userId}")
    @CrossOrigin(origins = "*")
    public CompanyDto addUserToCompany(@PathVariable Long companyId, @PathVariable Long userId) {
        return companyService.addUserToCompany(companyId, userId);
    }

    @PutMapping("/{companyId}")
    @CrossOrigin(origins = "*")
    public CompanyDto editCompanyInfo(@PathVariable Long companyId, @RequestBody CompanyDto companyDto){
        return companyService.editCompanyInfo(companyId, companyDto);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public List<CompanyDto> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/{companyId}")
    public CompanyDto getCompanyById(@PathVariable long companyId){
        return companyService.getCompanyById(companyId);
    }
}
