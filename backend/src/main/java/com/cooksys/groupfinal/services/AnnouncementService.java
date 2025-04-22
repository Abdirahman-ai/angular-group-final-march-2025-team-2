package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.AnnouncementDto;

public interface AnnouncementService {

    AnnouncementDto createAnnouncement(long companyID, AnnouncementDto announcementDto);
}
