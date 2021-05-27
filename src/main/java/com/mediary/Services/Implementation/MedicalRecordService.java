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
import com.mediary.Models.Enums.SortType;
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
import java.time.LocalDate;
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
        medicalRecordDto.setCategory(medicalRecordEntity.getCategory().getCode());
        medicalRecordDto.setNote(medicalRecordEntity.getNote());
        medicalRecordDto.setDateOfTheTest(medicalRecordEntity.getDateOfTheTest());
        medicalRecordDto.setUserId(medicalRecordEntity.getUserById().getId());

        var files = medicalRecordEntity.getFilesById();
        medicalRecordDto.setFiles(fileService.filesToDtos(files));

        var testItems = medicalRecordEntity.getTestItemsById();
        medicalRecordDto.setTestItems(testItemService.testItemsToDtos(testItems));

        return medicalRecordDto;
    }

    @Override
    @Transactional
    public int deleteMedicalRecord(UserDto user, Integer medicalRecordId) throws BlobStorageException, EntityNotFoundException {
        MedicalRecordEntity medicalRecord = medicalRecordRepository.findById(medicalRecordId);
        if(medicalRecord != null){
            if(medicalRecord.getUserById().getId().equals(user.getId())){
                Collection<TestItemEntity> medicalRecordItems = testItemRepository.findAllByMedicalRecordById(medicalRecord);
                for(TestItemEntity medicalRecordItem : medicalRecordItems){
                    testItemService.deleteTestItem(medicalRecordItem.getId());
                }
                List<FileEntity> files = fileRepository.findByMedicalRecordId(medicalRecordId);
                for(FileEntity file : files){
                    fileService.deleteFile(file.getId());
                }
                if(files.isEmpty()){
                    medicalRecordRepository.deleteById(medicalRecordId);
                    return Const.medicalRecordDeletionSuccess;
                }
                else{
                    return Const.medicalRecordFileDeletionError;
                }
            }
            return Const.medicalRecordDeletionError;
        }
        return Const.medicalRecordDoesNotExists;
    }

    @Override
    public List<GetMedicalRecordDto> getMedicalRecordsSorted(List<GetMedicalRecordDto> medicalRecords, String sortType){
        List<GetMedicalRecordDto> sortedResults = chooseSort(sortType, medicalRecords);
        return sortedResults;
    }

    @Override
    public List<GetMedicalRecordDto> sortByToday(List<GetMedicalRecordDto> medicalRecords) {
        List<GetMedicalRecordDto> sorted = new ArrayList<>();
        for (GetMedicalRecordDto medicalRecord: medicalRecords
        ) {
            if(medicalRecord.getDateOfTheTest().toLocalDate().equals(LocalDate.now()))
                sorted.add(medicalRecord);
        }
        return sorted;
    }

    @Override
    public List<GetMedicalRecordDto> sortByLastWeek(List<GetMedicalRecordDto> medicalRecords) {
        List<GetMedicalRecordDto> sorted = new ArrayList<>();
        for (GetMedicalRecordDto medicalRecord: medicalRecords
        ) {
            if(medicalRecord.getDateOfTheTest().toLocalDate().isAfter(LocalDate.now().minusDays(7))){
                sorted.add(medicalRecord);
            }

        }
        return sorted;
    }

    @Override
    public List<GetMedicalRecordDto> sortByLastMonth(List<GetMedicalRecordDto> medicalRecords) {
        List<GetMedicalRecordDto> sorted = new ArrayList<>();
        for (GetMedicalRecordDto medicalRecord: medicalRecords
        ) {
            if(medicalRecord.getDateOfTheTest().toLocalDate().isAfter(LocalDate.now().minusDays(30)))
                sorted.add(medicalRecord);
        }
        return sorted;
    }

    @Override
    public List<GetMedicalRecordDto> sortByLastYear(List<GetMedicalRecordDto> medicalRecords) {
        List<GetMedicalRecordDto> sorted = new ArrayList<>();
        for (GetMedicalRecordDto medicalRecord: medicalRecords
        ) {
            if(medicalRecord.getDateOfTheTest().toLocalDate().isAfter(LocalDate.now().minusDays(365)))
                sorted.add(medicalRecord);
        }
        return sorted;
    }

    @Override
    public List<GetMedicalRecordDto> sortByPast(List<GetMedicalRecordDto> medicalRecords) {
        List<GetMedicalRecordDto> sorted = new ArrayList<>();
        for (GetMedicalRecordDto medicalRecord: medicalRecords
        ) {
            if(medicalRecord.getDateOfTheTest().toLocalDate().isBefore(LocalDate.now()) ||
                    medicalRecord.getDateOfTheTest().toLocalDate().equals(LocalDate.now()))
                sorted.add(medicalRecord);
        }
        return sorted;
    }

    @Override
    public GetMedicalRecordDto updateMedicalRecordById(UpdateMedicalRecordDto medicalRecordDto, String authHeader, Integer medicalRecordId, MultipartFile[] updateFiles) throws EntityNotFoundException, EntityDoesNotBelongToUser, IncorrectFieldException, EnumConversionException, BlobStorageException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        MedicalRecordEntity updatedMedicalRecord = medicalRecordRepository.findById(medicalRecordId);
        List<Integer> ids = new ArrayList<>();
        List<Integer> filesIds = new ArrayList<>();
        if(updatedMedicalRecord != null){
            if (updatedMedicalRecord.getUserById().getId().equals(user.getId())) {
                if (medicalRecordDto.getTitle().length() > 50 || medicalRecordDto.getTitle() == "") {
                    throw new IncorrectFieldException("Title field is incorrect");
                } else if (medicalRecordDto.getLocation().length() > 50) {
                    throw new IncorrectFieldException("Location name is too long");
                } else if (medicalRecordDto.getNote().length() > 200) {
                    throw new IncorrectFieldException("Note is too long");
                } else if (medicalRecordDto.getDateOfTheTest() == null) {
                    throw new IncorrectFieldException("Date of the test is required");
                } else {

                    if(!medicalRecordDto.getTitle().equals(updatedMedicalRecord.getTitle()))
                        updatedMedicalRecord.setTitle(medicalRecordDto.getTitle());
                    if(!medicalRecordDto.getLocation().equals(updatedMedicalRecord.getLocation()))
                        updatedMedicalRecord.setLocation(medicalRecordDto.getLocation());
                    if(!medicalRecordDto.getCategory().equals(updatedMedicalRecord.getCategory().getCode()))
                        updatedMedicalRecord.setCategory(Category.convertStringToEnum(medicalRecordDto.getCategory()));
                    if(!medicalRecordDto.getNote().equals(updatedMedicalRecord.getNote()))
                        updatedMedicalRecord.setNote(medicalRecordDto.getNote());
                    if(!medicalRecordDto.getDateOfTheTest().equals(updatedMedicalRecord.getDateOfTheTest()))
                        updatedMedicalRecord.setDateOfTheTest(medicalRecordDto.getDateOfTheTest());

                    medicalRecordRepository.save(updatedMedicalRecord);
                    Collection<AddTestItemDto> newTestItems = medicalRecordDto.getNewTestItems();
                    List<GetTestItemDto> testItems = testItemService.getAllByMedicalRecordId(updatedMedicalRecord.getId());
                    List<FileEntity> files = fileService.getFilesByMedicalRecord(medicalRecordId);
                    Collection<UpdateTestItemDto> medicalRecordItems = medicalRecordDto.getTestItems();
                    Collection<GetFileDto> fileDtos = medicalRecordDto.getFiles();
                    MultipartFile[] newFiles = medicalRecordDto.getNewFiles();
                    if(medicalRecordItems != null){
                        for(UpdateTestItemDto medicalRecordItem : medicalRecordItems){
                            ids.add(medicalRecordItem.getId());
                        }
                    }
                    if(fileDtos != null){
                        for(GetFileDto fileDto : fileDtos){
                            filesIds.add(fileDto.getId());
                        }
                    }
                    if(medicalRecordItems != null){
                        for(GetTestItemDto testItem : testItems){
                            if(ids.contains(testItem.getId())){
                                for(UpdateTestItemDto medicalRecordItem : medicalRecordItems){
                                    this.updateTestItemById(medicalRecordItem, authHeader, medicalRecordItem.getId(), updatedMedicalRecord);
                                }
                            }
                            if(!ids.contains(testItem.getId())){
                                testItemService.deleteTestItem(testItem.getId());
                            }
                        }
                    }
                    if(newTestItems != null){
                        for(AddTestItemDto newTestItem : newTestItems){
                            System.out.println(newTestItem.getName());
                            testItemService.addTestItem(newTestItem, updatedMedicalRecord);
                        }
                    }
                    if(newFiles != null){
                        for (MultipartFile newFile : newFiles){
                            fileService.uploadFile(newFile, user.getId(), updatedMedicalRecord);
                        }
                    }
                    if(fileDtos != null){
                        for(FileEntity file : files){
                            if(filesIds.contains(file.getId())){
                                for(GetFileDto fileDto : fileDtos){
                                    boolean isFileDeleted = fileService.deleteFile(fileDto.getId());
                                    if(isFileDeleted == true){
                                        for (MultipartFile updateFile : updateFiles) {
                                            fileService.uploadFile(updateFile, user.getId(), updatedMedicalRecord);
                                        }
                                    }
                                }
                            }
                            if(!filesIds.contains(file.getId())){
                                fileService.deleteFile(file.getId());
                            }
                        }
                    }
                }
            } else {
                log.warn("Medical record doesn't belong to this user");
                throw new EntityDoesNotBelongToUser("Medical record doesn't belong to this user!");
            }

        } else{
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
        if(updatedTestItem != null){
            for(MedicalRecordEntity medicalRecord : medicalRecords){
                List<TestItemEntity> medicalRecordsItems = (List<TestItemEntity>) testItemRepository.findAllByMedicalRecordById(medicalRecord);
                for(TestItemEntity medicalRecordItem : medicalRecordsItems){
                    ids.add(medicalRecordItem.getId());
                }
            }
            if(ids.contains(updatedTestItem.getId()) == true){
                if (medicalRecordItemDto.getName().length() > 40 || medicalRecordItemDto.getName() == "") {
                    throw new IncorrectFieldException("Name field is incorrect");
                } else if (medicalRecordItemDto.getUnit().length() > 10) {
                    throw new IncorrectFieldException("Unit name is too long");
                } else if (medicalRecordItemDto.getValue().length() > 50) {
                    throw new IncorrectFieldException("Value is too long");
                } else {
                    if(!medicalRecordItemDto.getName().equals(updatedTestItem.getName()))
                        updatedTestItem.setName(medicalRecordItemDto.getName());
                    if(!medicalRecordItemDto.getUnit().equals(updatedTestItem.getUnit()))
                        updatedTestItem.setUnit(medicalRecordItemDto.getUnit());
                    if(!medicalRecordItemDto.getValue().equals(updatedTestItem.getValue()))
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
                ArrayList<GetMedicalRecordDto> medicalRecordDtos = (ArrayList<GetMedicalRecordDto>) medicalRecordsToDtos(
                        medicalRecords);
                return medicalRecordDtos;
            } else {
                throw new EntityNotFoundException("User doesn't exist.");
            }
        }

    private List<GetMedicalRecordDto> chooseSort(String sortType,  List<GetMedicalRecordDto> medicalRecords) {
        List<GetMedicalRecordDto> sortedEvents = null;
        if(sortType.equals(SortType.Today.toString())) sortedEvents = sortByToday(medicalRecords);
        if(sortType.equals(SortType.LastWeek.toString())) sortedEvents = sortByLastWeek(medicalRecords);
        if(sortType.equals(SortType.LastMonth.toString())) sortedEvents = sortByLastMonth(medicalRecords);
        if(sortType.equals(SortType.LastYear.toString())) sortedEvents = sortByLastYear(medicalRecords);
        if(sortType.equals(SortType.Past.toString())) sortedEvents = sortByPast(medicalRecords);
        return sortedEvents;
    }
}
