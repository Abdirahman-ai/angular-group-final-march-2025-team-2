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

	@PatchMapping("/{announcementId}")
	public AnnouncementDto patchAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementDto updateDto){
		return announcementService.updateAnnouncement(announcementId, updateDto);
	}

}
