package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.mappers.AnnouncementMapper;
import com.cooksys.groupfinal.repositories.AnnouncementRepository;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.services.AnnouncementService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final AnnouncementMapper announcementMapper;
    private final AnnouncementRepository announcementRepository;

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

    @Override
    public AnnouncementDto createAnnouncement(long companyID, AnnouncementDto announcementDto) {
        Optional<Company> companyOpt = companyRepository.findById(companyID);
        if(companyOpt.isEmpty()){
            throw new BadRequestException("Company with company ID: " + companyID + " does not exist!");
        }
        Company company = companyOpt.get();
        Announcement newAnnouncement = new Announcement();

        Announcement announcement = announcementMapper.dtoToEntity(announcementDto);
        Optional<User> user = userRepository.findById(announcement.getAuthor().getId());
        user.ifPresent(newAnnouncement::setAuthor);

        newAnnouncement.setTitle(announcementDto.getTitle());
        newAnnouncement.setMessage(announcementDto.getMessage());
        newAnnouncement.setCompany(company);

        announcementRepository.saveAndFlush(newAnnouncement);
        return announcementMapper.entityToDto(newAnnouncement);
    }

    @Override
    public void deleteAnnouncement(Long announcementId) {
        if(!announcementRepository.existsById(announcementId)){
            throw new NotFoundException("Announcement is not found");
        }
        announcementRepository.deleteById(announcementId);
    }
}