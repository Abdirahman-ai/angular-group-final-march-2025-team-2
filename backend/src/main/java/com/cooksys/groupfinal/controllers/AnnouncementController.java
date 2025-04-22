package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.AnnouncementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
	
	private final AnnouncementService announcementService;

	@PostMapping("/company/{companyID}")
	public AnnouncementDto createAnnouncement(@PathVariable long companyID, @RequestBody AnnouncementDto announcementDto){
		return announcementService.createAnnouncement(companyID, announcementDto);
	}
}
