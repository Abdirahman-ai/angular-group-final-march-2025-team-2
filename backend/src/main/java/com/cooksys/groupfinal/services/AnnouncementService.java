package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.AnnouncementDto;

public interface AnnouncementService {

    AnnouncementDto updateAnnouncement(Long id, AnnouncementDto announcementDto);

    AnnouncementDto createAnnouncement(long companyID, AnnouncementDto announcementDto);
}
