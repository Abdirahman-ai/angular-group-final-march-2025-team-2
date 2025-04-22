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
}