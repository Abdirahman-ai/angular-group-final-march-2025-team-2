package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.UserRequestDto;
import org.springframework.web.bind.annotation.*;
import com.cooksys.groupfinal.dtos.StatusUpdateDto;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	private final UserService userService;

	@PostMapping("/login")
    public FullUserDto login(@RequestBody CredentialsDto credentialsDto) {
        return userService.login(credentialsDto);
    }

    @GetMapping("/{id}")
    public FullUserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<FullUserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public FullUserDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PutMapping("/{id}")
    public FullUserDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(id, userRequestDto);
    }

    @PatchMapping("/{id}/status")
    public FullUserDto updateUserStatus(@PathVariable Long id, @RequestBody StatusUpdateDto statusUpdateDto){
        return userService.updateUserStatus(id, statusUpdateDto.getStatus());
    }
}
