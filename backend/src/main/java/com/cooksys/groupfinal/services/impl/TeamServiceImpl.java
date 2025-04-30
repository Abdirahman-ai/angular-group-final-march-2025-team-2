package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.TeamMapper;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.dtos.BasicUserDto;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.services.TeamService;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    public TeamDto removeUser(Long teamId, Long userId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) {
            throw new NotFoundException("A team with the provided id does not exist.");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new NotFoundException("A user with the provided id does not exist");
        }

        Team team = teamOptional.get();
        User user = userOptional.get();

        Set<User> teammates = team.getTeammates();
        if(!teammates.contains(user)){
            throw new NotFoundException("The user is not a member of this team");
        }

        teammates.remove(user);
        team.setTeammates(teammates);

        Team savedTeam = teamRepository.save(team);
        return teamMapper.entityToDto(savedTeam);

    }

    @Override
    public TeamDto createTeam(Long companyId, TeamDto teamDto) {
        Optional<Company> companyOpt = companyRepository.findById(companyId);
        if (companyOpt.isEmpty()) {
            throw new NotFoundException("Company with id " + companyId + " not found.");
        }

        Company company = companyOpt.get();

        Team team = new Team();
        team.setName(teamDto.getName());
        team.setDescription(teamDto.getDescription());
        team.setCompany(company);

        // Add teammates if provided
        if (teamDto.getTeammates() != null) {
            Set<User> teammates = new HashSet<>();
            for (BasicUserDto userDto : teamDto.getTeammates()) {
                if (userDto.getId() != null) {
                    Optional<User> userOpt = userRepository.findById(userDto.getId());
                    userOpt.ifPresent(teammates::add);
                }
            }
            team.setTeammates(teammates);
        }

        return teamMapper.entityToDto(teamRepository.saveAndFlush(team));
    }

    @Override
    public TeamDto addUserToTeam(Long teamId, Long userId) {
        Optional<Team> team = teamRepository.findById(teamId);

        if(team.isEmpty()){
            throw new BadRequestException("Team with id " + teamId + "not found");
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()){
            throw new NotFoundException("User with id " + userId + " not found.");
        }
        if(!userOpt.get().isActive()){
            throw new BadRequestException("Cannot add inactive user to a team.");
        }

        team.get().getTeammates().add(userOpt.get());
        userOpt.get().getTeams().add(team.get());

        return teamMapper.entityToDto(teamRepository.saveAndFlush(team.get()));
    }

    @Override
    public TeamDto updateTeam(Long teamId, TeamDto teamDto) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);

        if(teamOptional.isEmpty()){
            throw new NotFoundException("The team is not found");
        }

        Team exisingTeam = teamOptional.get();

        exisingTeam.setName(teamDto.getName());
        exisingTeam.setDescription(teamDto.getDescription());

        if (teamDto.getTeammates() != null) {
            Set<User> updatedTeammates = new HashSet<>();

            for (BasicUserDto userDto : teamDto.getTeammates()) {
                if (userDto.getId() != null) {
                    Optional<User> userOpt = userRepository.findById(userDto.getId());
                    if (userOpt.isPresent()) {
                        updatedTeammates.add(userOpt.get());
                    }
                }
            }

            exisingTeam.setTeammates(updatedTeammates);
        }
        Team updatedTeam = teamRepository.save(exisingTeam);
        return teamMapper.entityToDto(updatedTeam);
    }

    @Override
    public void deleteTeam(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new NotFoundException("Team not found with ID: " + teamId);
        }
        teamRepository.deleteById(teamId);
    }

    @Override
    public TeamDto getTeamById(Long teamId) {
        if(!teamRepository.existsById(teamId)){
            throw new NotFoundException("Team not found with Id" + teamId);
        }
        return teamMapper.entityToDto(teamRepository.findById(teamId).get());
    }
}
