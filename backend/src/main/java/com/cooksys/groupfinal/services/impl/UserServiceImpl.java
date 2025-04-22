package com.cooksys.groupfinal.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.cooksys.groupfinal.dtos.UserRequestDto;
import com.cooksys.groupfinal.entities.Profile;
import com.cooksys.groupfinal.entities.Project;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.entities.Credentials;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotAuthorizedException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.CredentialsMapper;
import com.cooksys.groupfinal.mappers.FullUserMapper;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
  private final FullUserMapper fullUserMapper;
	private final CredentialsMapper credentialsMapper;
	
	private User findUser(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndActiveTrue(username);
        if (user.isEmpty()) {
            throw new NotFoundException("The username provided does not belong to an active user.");
        }
        return user.get();
    }
	
	@Override
	public FullUserDto login(CredentialsDto credentialsDto) {
		if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
            throw new BadRequestException("A username and password are required.");
        }
        Credentials credentialsToValidate = credentialsMapper.dtoToEntity(credentialsDto);
        User userToValidate = findUser(credentialsDto.getUsername());
        if (!userToValidate.getCredentials().equals(credentialsToValidate)) {
            throw new NotAuthorizedException("The provided credentials are invalid.");
        }
        if (userToValidate.getStatus().equals("PENDING")) {
        	userToValidate.setStatus("JOINED");
        	userRepository.saveAndFlush(userToValidate);
        }
        return fullUserMapper.entityToFullUserDto(userToValidate);
	}

    @Override
    public FullUserDto getUserById(long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty() || !userOpt.get().isActive()) {
            throw new NotFoundException("User with ID " + id + " not found or inactive.");
        }
        return fullUserMapper.entityToFullUserDto(userOpt.get());
    }

    @Override
    public List<FullUserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<FullUserDto> activeUsers = new ArrayList<>();

        for (User user : allUsers) {
            if (user.isActive()) {
                FullUserDto dto = fullUserMapper.entityToFullUserDto(user);
                activeUsers.add(dto);
            }
        }
        return activeUsers;
    }

    @Override
    public FullUserDto createUser(UserRequestDto userRequestDto) {
        if (userRequestDto == null || userRequestDto.getCredentials() == null || userRequestDto.getProfile() == null) {
            throw new BadRequestException("Missing required user information.");
        }

        User user = fullUserMapper.requestDtoToEntity(userRequestDto);

        Credentials creds = user.getCredentials();
        if (creds.getUsername() == null || creds.getPassword() == null ||
                creds.getUsername().trim().isEmpty() || creds.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Username and password are required.");
        }

        Profile profile = user.getProfile();
        if (profile.getEmail() == null || profile.getPhone() == null ||
                profile.getFirstName() == null || profile.getLastName() == null) {
            throw new BadRequestException("Profile information is incomplete.");
        }

        user.setActive(true);
        user.setStatus("PENDING");

        return fullUserMapper.entityToFullUserDto(userRepository.saveAndFlush(user));
    }
}
