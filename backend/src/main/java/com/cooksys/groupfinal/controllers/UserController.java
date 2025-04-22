package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.UserRequestDto;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/login")
	@CrossOrigin(origins="*")
    public FullUserDto login(@RequestBody CredentialsDto credentialsDto) {
        return userService.login(credentialsDto);
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public FullUserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public List<FullUserDto> getAllUsers(){
        return userService.getAllUsers();
    }
}
