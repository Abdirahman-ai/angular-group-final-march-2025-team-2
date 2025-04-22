package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.AnnouncementMapper;
import com.cooksys.groupfinal.repositories.AnnouncementRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.services.AnnouncementService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    @Override
    public AnnouncementDto updateAnnouncement(Long id, AnnouncementDto announcementDto) {
        Optional<Announcement> announcementOptional =  announcementRepository.findById(id);
        if(announcementOptional.isEmpty()){
            throw new NotFoundException("The announcement cannot be found");
        }

        Announcement announcement = announcementOptional.get();

        if (announcementDto.getTitle() != null) {
            announcement.setTitle(announcementDto.getTitle());
        }

        if (announcementDto.getMessage() != null) {
            announcement.setMessage(announcementDto.getMessage());
        }

        Announcement updated = announcementRepository.save(announcement);
        return announcementMapper.entityToDto(updated);

    }
}