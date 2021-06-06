package com.mediary.Services.Implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediary.Models.DTOs.Request.AddMedicalRecordDto;
import com.mediary.Models.DTOs.Request.AddTestItemDto;
import com.mediary.Models.DTOs.Request.UpdateMedicalRecordDto;
import com.mediary.Models.DTOs.Request.UpdateTestItemDto;
import com.mediary.Models.DTOs.Response.GetFileDto;
import com.mediary.Models.DTOs.Response.GetMedicalRecordDto;
import com.mediary.Models.DTOs.Response.GetTestItemDto;
import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.Entities.FileEntity;
import com.mediary.Models.Entities.MedicalRecordEntity;
import com.mediary.Models.Entities.TestItemEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Models.Enums.Category;
import com.mediary.Repositories.FileRepository;
import com.mediary.Repositories.MedicalRecordRepository;
import com.mediary.Repositories.TestItemRepository;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.*;
import com.mediary.Services.Interfaces.IFileService;
import com.mediary.Services.Interfaces.IMedicalRecordService;
import com.mediary.Services.Interfaces.ITestItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class MedicalRecordService implements IMedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    UserService userService;

    @Autowired
    IFileService fileService;

    @Autowired
    ITestItemService testItemService;

    @Autowired
    TestItemRepository testItemRepository;

    @Autowired
    FileRepository fileRepository;

    @Override
    public GetMedicalRecordDto addMedicalRecordByAuthHeader(AddMedicalRecordDto medicalRecord, MultipartFile[] files, String authHeader)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException, EnumConversionException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        return addMedicalRecord(medicalRecord, files, user);
    }

    @Override
    public GetMedicalRecordDto addMedicalRecord(AddMedicalRecordDto medicalRecordDto, MultipartFile[] files, UserEntity user)
            throws IncorrectFieldException, BlobStorageException, EnumConversionException {

        if (medicalRecordDto.getTitle().length() > 50 || medicalRecordDto.getTitle().equals("")) {
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
            medicalRecordEntity.setCategory(Category.convertStringToEnum(medicalRecordDto.getCategory()));
            medicalRecordEntity.setNote(medicalRecordDto.getNote());
            medicalRecordEntity.setDateOfTheTest(new Timestamp(medicalRecordDto.getDateOfTheTest().getTime()));
            medicalRecordEntity.setUserById(user);

            var savedEntity = medicalRecordRepository.saveAndFlush(medicalRecordEntity);

            if (files != null) {
                for (MultipartFile file : files) {
                    fileService.uploadFile(file, user.getId(), savedEntity);
                }
            }

            testItemService.addTestItems(medicalRecordDto.getTestItems(), savedEntity);
            return medicalRecordToDto(savedEntity);
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
        return medicalRecordsToDtos(medicalRecordEntities);

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
        medicalRecordDto.setCategory(medicalRecordEntity.getCategory().getCode());
        medicalRecordDto.setNote(medicalRecordEntity.getNote());
        medicalRecordDto.setDateOfTheTest(medicalRecordEntity.getDateOfTheTest());
        medicalRecordDto.setUserId(medicalRecordEntity.getUserById().getId());

        var files = fileRepository.findByMedicalRecordId(medicalRecordEntity.getId());
        medicalRecordDto.setFiles(fileService.filesToDtos(files));

        var testItems = testItemRepository.findByMedicalRecordId(medicalRecordEntity.getId());
        medicalRecordDto.setTestItems(testItemService.testItemsToDtos(testItems));

        return medicalRecordDto;
    }

    @Override
    @Transactional
    public int deleteMedicalRecord(UserDto user, Integer medicalRecordId) throws BlobStorageException, EntityNotFoundException {
        MedicalRecordEntity medicalRecord = medicalRecordRepository.findById(medicalRecordId);
        if (medicalRecord != null) {
            if (medicalRecord.getUserById().getId().equals(user.getId())) {
                Collection<TestItemEntity> medicalRecordItems = testItemRepository.findAllByMedicalRecordById(medicalRecord);
                for (TestItemEntity medicalRecordItem : medicalRecordItems) {
                    testItemService.deleteTestItem(medicalRecordItem.getId());
                }
                List<FileEntity> files = fileRepository.findByMedicalRecordId(medicalRecordId);
                for (FileEntity file : files) {
                    fileService.deleteFile(file.getId());
                }

                files = fileRepository.findByMedicalRecordId(medicalRecordId);
                if (files.isEmpty()) {
                    medicalRecordRepository.deleteById(medicalRecordId);
                    return Const.medicalRecordDeletionSuccess;
                } else {
                    return Const.medicalRecordFileDeletionError;
                }
            }
            return Const.medicalRecordDeletionError;
        }
        return Const.medicalRecordDoesNotExists;
    }

    @Override
    @Transactional
    public GetMedicalRecordDto updateMedicalRecordById(UpdateMedicalRecordDto medicalRecordDto, String authHeader, MultipartFile[] newFiles) throws EntityNotFoundException, EntityDoesNotBelongToUser, IncorrectFieldException, EnumConversionException, BlobStorageException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        MedicalRecordEntity updatedMedicalRecord = medicalRecordRepository.findById(medicalRecordDto.getId());
        if (updatedMedicalRecord != null) {
            if (updatedMedicalRecord.getUserById().getId().equals(user.getId())) {
                if (medicalRecordDto.getTitle().length() > 50 || medicalRecordDto.getTitle().equals("")) {
                    throw new IncorrectFieldException("Title field is incorrect");
                } else if (medicalRecordDto.getLocation().length() > 50) {
                    throw new IncorrectFieldException("Location name is too long");
                } else if (medicalRecordDto.getNote().length() > 200) {
                    throw new IncorrectFieldException("Note is too long");
                } else if (medicalRecordDto.getDateOfTheTest() == null) {
                    throw new IncorrectFieldException("Date of the test is required");
                } else {

                    if (!medicalRecordDto.getTitle().equals(updatedMedicalRecord.getTitle()))
                        updatedMedicalRecord.setTitle(medicalRecordDto.getTitle());
                    if (!medicalRecordDto.getLocation().equals(updatedMedicalRecord.getLocation()))
                        updatedMedicalRecord.setLocation(medicalRecordDto.getLocation());
                    if (!medicalRecordDto.getCategory().equals(updatedMedicalRecord.getCategory().getCode()))
                        updatedMedicalRecord.setCategory(Category.convertStringToEnum(medicalRecordDto.getCategory()));
                    if (!medicalRecordDto.getNote().equals(updatedMedicalRecord.getNote()))
                        updatedMedicalRecord.setNote(medicalRecordDto.getNote());
                    if (medicalRecordDto.getDateOfTheTest().getTime() != updatedMedicalRecord.getDateOfTheTest().getTime())
                        updatedMedicalRecord.setDateOfTheTest(new Timestamp(medicalRecordDto.getDateOfTheTest().getTime()));

                    medicalRecordRepository.save(updatedMedicalRecord);
                    Collection<AddTestItemDto> newTestItems = medicalRecordDto.getNewTestItems();
                    List<GetTestItemDto> testItems = testItemService.getAllByMedicalRecordId(updatedMedicalRecord.getId());
                    List<FileEntity> files = fileService.getFilesByMedicalRecord(medicalRecordDto.getId());
                    Collection<UpdateTestItemDto> dtoTestItems = medicalRecordDto.getTestItems();
                    Collection<GetFileDto> dtoFiles = medicalRecordDto.getFiles();

                    for (FileEntity file : files) {
                        if (dtoFiles.stream().noneMatch(x -> x.getId().equals(file.getId()))) {
                            fileService.deleteFile(file.getId());
                        }
                    }

                    if (newFiles != null) {
                        for (MultipartFile newFile : newFiles) {
                            fileService.uploadFile(newFile, user.getId(), updatedMedicalRecord);
                        }
                    }

                    for (GetTestItemDto testItem : testItems) {
                        if (dtoTestItems.stream().noneMatch(x -> x.getId().equals(testItem.getId()))) {
                            testItemService.deleteTestItem(testItem.getId());
                        } else {
                            var updatedTestItem = dtoTestItems.stream()
                                    .filter(x -> x.getId().equals(testItem.getId()))
                                    .findFirst();

                            updateTestItemById(updatedTestItem.orElseThrow(), authHeader, testItem.getId(), updatedMedicalRecord);
                        }
                    }

                    for (AddTestItemDto newTestItem : newTestItems) {
                        testItemService.addTestItem(newTestItem, updatedMedicalRecord);
                    }
                }
            } else {
                log.warn("Medical record doesn't belong to this user");
                throw new EntityDoesNotBelongToUser("Medical record doesn't belong to this user!");
            }

        } else {
            log.warn("Medical record doesn't exist");
            throw new EntityNotFoundException("Medical record with specified id doesn't exist");
        }
        return medicalRecordToDto(updatedMedicalRecord);
    }

    @Override
    public void updateTestItemById(UpdateTestItemDto medicalRecordItemDto, String authHeader, Integer medicalRecordItemId, MedicalRecordEntity medicalRecordEntity) throws EntityNotFoundException, IncorrectFieldException, EntityDoesNotBelongToUser {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        List<Integer> ids = new ArrayList<>();
        TestItemEntity updatedTestItem = testItemRepository.findById(medicalRecordItemId);
        List<MedicalRecordEntity> medicalRecords = medicalRecordRepository.findByUserId(user.getId());
        if (updatedTestItem != null) {
            for (MedicalRecordEntity medicalRecord : medicalRecords) {
                List<TestItemEntity> medicalRecordsItems = (List<TestItemEntity>) testItemRepository.findAllByMedicalRecordById(medicalRecord);
                for (TestItemEntity medicalRecordItem : medicalRecordsItems) {
                    ids.add(medicalRecordItem.getId());
                }
            }
            if (ids.contains(updatedTestItem.getId())) {
                if (medicalRecordItemDto.getName().length() > 40 || medicalRecordItemDto.getName() == "") {
                    throw new IncorrectFieldException("Name field is incorrect");
                } else if (medicalRecordItemDto.getUnit().length() > 10) {
                    throw new IncorrectFieldException("Unit name is too long");
                } else if (medicalRecordItemDto.getValue().length() > 50) {
                    throw new IncorrectFieldException("Value is too long");
                } else {
                    if (!medicalRecordItemDto.getName().equals(updatedTestItem.getName()))
                        updatedTestItem.setName(medicalRecordItemDto.getName());
                    if (!medicalRecordItemDto.getUnit().equals(updatedTestItem.getUnit()))
                        updatedTestItem.setUnit(medicalRecordItemDto.getUnit());
                    if (!medicalRecordItemDto.getValue().equals(updatedTestItem.getValue()))
                        updatedTestItem.setValue(medicalRecordItemDto.getValue());
                    testItemRepository.save(updatedTestItem);
                }
            } else {
                log.warn("Medical record item doesn't belong to this user");
                throw new EntityDoesNotBelongToUser("Medical record item doesn't belong to this user!");
            }
        } else {
            log.warn("Test item doesn't exist");
            throw new EntityNotFoundException("Test item with specified id doesn't exist");
        }
    }

    @Override
    public List<GetMedicalRecordDto> getScheduleItemByAuthHeaderAndDate(String authHeader, String dateFrom, String dateTo) throws EntityNotFoundException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        if (user != null) {
            var medicalRecords = medicalRecordRepository.findByUserByIdAndDateOfTheTestBetweenOrderByDateOfTheTest(java.util.Optional.of(user),
                    Timestamp.valueOf(dateFrom + " 00:00:00"), Timestamp.valueOf(dateTo + " 23:59:59"));
            return medicalRecordsToDtos(medicalRecords);
        } else {
            throw new EntityNotFoundException("User doesn't exist.");
        }
    }
}
