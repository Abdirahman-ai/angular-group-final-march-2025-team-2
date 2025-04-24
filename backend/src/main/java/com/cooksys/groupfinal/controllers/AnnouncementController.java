package com.cooksys.groupfinal.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.AnnouncementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AnnouncementController {
	
	private final AnnouncementService announcementService;

	@PatchMapping("/{announcementId}")
	public AnnouncementDto patchAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementDto updateDto){
		return announcementService.updateAnnouncement(announcementId, updateDto);
	}

	@PostMapping("/company/{companyID}")
	public AnnouncementDto createAnnouncement(@PathVariable long companyID, @RequestBody AnnouncementDto announcementDto){
		return announcementService.createAnnouncement(companyID, announcementDto);
	}
}
