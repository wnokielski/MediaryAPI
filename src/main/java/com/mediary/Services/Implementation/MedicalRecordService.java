package com.mediary.Services.Implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediary.Models.DTOs.Request.AddMedicalRecordDto;
import com.mediary.Models.DTOs.Response.GetMedicalRecordDto;
import com.mediary.Models.Entities.MedicalRecordEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Repositories.MedicalRecordRepository;
import com.mediary.Repositories.TestTypeRepository;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IFileService;
import com.mediary.Services.Interfaces.ITestItemService;
import com.mediary.Services.Interfaces.IMedicalRecordService;
import com.mediary.Services.Interfaces.ITestTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicalRecordService implements IMedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    TestTypeRepository testTypeRepository;

    @Autowired
    UserService userService;

    @Autowired
    IFileService fileService;

    @Autowired
    ITestTypeService testTypeService;

    @Autowired
    ITestItemService testItemService;

    @Override
    public void addMedicalRecordByAuthHeader(AddMedicalRecordDto medicalRecord, MultipartFile[] files, String authHeader)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        addMedicalRecord(medicalRecord, files, user);
    }

    @Override
    public void addMedicalRecord(AddMedicalRecordDto medicalRecordDto, MultipartFile[] files, UserEntity user)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException {

        if (medicalRecordDto.getTitle().length() > 50 || medicalRecordDto.getTitle() == "") {
            throw new IncorrectFieldException("Title field is incorrect");
        } else if (medicalRecordDto.getLocation().length() > 50) {
            throw new IncorrectFieldException("Location name is too long");
        } else if (medicalRecordDto.getNote().length() > 200) {
            throw new IncorrectFieldException("Note is too long");
        } else if (medicalRecordDto.getDateOfTheTest() == null) {
            throw new IncorrectFieldException("Date of the test is required");
        } else {
            MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
            medicalRecordEntity.setTitle(medicalRecordDto.getTitle());
            medicalRecordEntity.setLocation(medicalRecordDto.getLocation());
            medicalRecordEntity.setNote(medicalRecordDto.getNote());
            medicalRecordEntity.setDateOfTheTest(medicalRecordDto.getDateOfTheTest());

            var testType = testTypeRepository.findById(medicalRecordDto.getTestTypeId());
            if (testType != null) {
                medicalRecordEntity.setTestTypeById(testType);
            } else {
                throw new EntityNotFoundException("Test Type with specified id doesn't exist");
            }

            medicalRecordEntity.setUserById(user);

            for (MultipartFile file : files) {
                fileService.uploadFile(file, user.getId(), medicalRecordEntity);
            }

            testItemService.addTestItems(medicalRecordDto.getTestItems(), medicalRecordEntity);
            medicalRecordRepository.save(medicalRecordEntity);
        }
    }

    @Override
    public AddMedicalRecordDto getJson(String medicalRecord) {
        AddMedicalRecordDto medicalRecordDto = new AddMedicalRecordDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            medicalRecordDto = objectMapper.readValue(medicalRecord, AddMedicalRecordDto.class);
        } catch (IOException e) {
            log.warn("Mapping error");
        }
        return medicalRecordDto;
    }

    @Override
    public List<GetMedicalRecordDto> getMedicalRecordsByAuthHeader(String authHeader) throws EntityNotFoundException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        return getMedicalRecordsByUser(user);
    }

    @Override
    public List<GetMedicalRecordDto> getMedicalRecordsByUser(UserEntity user) {
        var medicalRecordEntities = medicalRecordRepository.findByUserId(user.getId());
        List<GetMedicalRecordDto> medicalRecordDtos = medicalRecordsToDtos(medicalRecordEntities);
        return medicalRecordDtos;

    }

    @Override
    public List<GetMedicalRecordDto> medicalRecordsToDtos(List<MedicalRecordEntity> medicalRecordEntities) {
        List<GetMedicalRecordDto> medicalRecordDtos = new ArrayList<GetMedicalRecordDto>();

        for (MedicalRecordEntity medicalRecordEntity : medicalRecordEntities) {
            var medicalRecordDto = medicalRecordToDto(medicalRecordEntity);
            medicalRecordDtos.add(medicalRecordDto);
        }
        return medicalRecordDtos;
    }

    @Override
    public GetMedicalRecordDto medicalRecordToDto(MedicalRecordEntity medicalRecordEntity) {
        GetMedicalRecordDto medicalRecordDto = new GetMedicalRecordDto();
        medicalRecordDto.setId(medicalRecordEntity.getId());
        medicalRecordDto.setTitle(medicalRecordEntity.getTitle());
        medicalRecordDto.setLocation(medicalRecordEntity.getLocation());
        medicalRecordDto.setNote(medicalRecordEntity.getNote());
        medicalRecordDto.setDateOfTheTest(medicalRecordEntity.getDateOfTheTest());
        medicalRecordDto.setUserId(medicalRecordEntity.getUserById().getId());

        var testTypeEntity = medicalRecordEntity.getTestTypeById();
        medicalRecordDto.setTestType(testTypeService.testTypeToDto(testTypeEntity));

        var files = medicalRecordEntity.getFilesById();
        medicalRecordDto.setFiles(fileService.filesToDtos(files));

        var testItems = medicalRecordEntity.getTestItemsById();
        medicalRecordDto.setTestItems(testItemService.testItemsToDtos(testItems));

        return medicalRecordDto;
    }
}
