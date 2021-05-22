package com.mediary.Services.Implementation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediary.Models.DTOs.Request.AddTestResultDto;
import com.mediary.Models.DTOs.Response.GetTestResultDto;
import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.Entities.*;
import com.mediary.Models.Enums.SortType;
import com.mediary.Repositories.FileRepository;
import com.mediary.Repositories.TestResultItemRepository;
import com.mediary.Repositories.TestResultRepository;
import com.mediary.Repositories.TestTypeRepository;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IFileService;
import com.mediary.Services.Interfaces.ITestResultItemService;
import com.mediary.Services.Interfaces.ITestResultService;
import com.mediary.Services.Interfaces.ITestTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestResultService implements ITestResultService {

    @Autowired
    TestResultRepository testResultRepository;

    @Autowired
    TestTypeRepository testTypeRepository;

    @Autowired
    UserService userService;

    @Autowired
    IFileService fileService;

    @Autowired
    ITestTypeService testTypeService;

    @Autowired
    ITestResultItemService testResultItemService;

    @Autowired
    TestResultItemRepository testResultItemRepository;

    @Autowired
    FileRepository fileRepository;

    @Override
    public void addTestResultByAuthHeader(AddTestResultDto testResult, MultipartFile[] files, String authHeader)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        addTestResult(testResult, files, user);
    }

    @Override
    public void addTestResult(AddTestResultDto testResultDto, MultipartFile[] files, UserEntity user)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException {

        if (testResultDto.getTitle().length() > 50 || testResultDto.getTitle() == "") {
            throw new IncorrectFieldException("Title field is incorrect");
        } else if (testResultDto.getLocation().length() > 50) {
            throw new IncorrectFieldException("Location name is too long");
        } else if (testResultDto.getNote().length() > 200) {
            throw new IncorrectFieldException("Note is too long");
        } else if (testResultDto.getDateOfTheTest() == null) {
            throw new IncorrectFieldException("Date of the test is required");
        } else {
            TestResultEntity testResultEntity = new TestResultEntity();
            testResultEntity.setTitle(testResultDto.getTitle());
            testResultEntity.setLocation(testResultDto.getLocation());
            testResultEntity.setNote(testResultDto.getNote());
            testResultEntity.setDateOfTheTest(testResultDto.getDateOfTheTest());

            var testType = testTypeRepository.findById(testResultDto.getTestTypeId());
            if (testType != null) {
                testResultEntity.setTestTypeById(testType);
            } else {
                throw new EntityNotFoundException("Test Type with specified id doesn't exist");
            }

            testResultEntity.setUserById(user);

            for (MultipartFile file : files) {
                fileService.uploadFile(file, user.getId(), testResultEntity);
            }

            testResultItemService.addTestResultItems(testResultDto.getTestResulItems(), testResultEntity);
            testResultRepository.save(testResultEntity);
        }
    }

    @Override
    public AddTestResultDto getJson(String testResult) {
        AddTestResultDto testResultDto = new AddTestResultDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            testResultDto = objectMapper.readValue(testResult, AddTestResultDto.class);
        } catch (IOException e) {
            log.warn("Mapping error");
        }
        return testResultDto;
    }

    @Override
    public List<GetTestResultDto> getTestResultsByAuthHeader(String authHeader) throws EntityNotFoundException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        return getTestResultsByUser(user);
    }

    @Override
    public List<GetTestResultDto> getTestResultsByUser(UserEntity user) {
        var testResultEntities = testResultRepository.findByUserId(user.getId());
        List<GetTestResultDto> testResultDtos = testResultsToDtos(testResultEntities);
        return testResultDtos;

    }

    @Override
    public List<GetTestResultDto> testResultsToDtos(List<TestResultEntity> testResultEntities) {
        List<GetTestResultDto> testResultDtos = new ArrayList<GetTestResultDto>();

        for (TestResultEntity testResultEntity : testResultEntities) {
            var testResultDto = testResultToDto(testResultEntity);
            testResultDtos.add(testResultDto);
        }
        return testResultDtos;
    }

    @Override
    public GetTestResultDto testResultToDto(TestResultEntity testResultEntity) {
        GetTestResultDto testResultDto = new GetTestResultDto();
        testResultDto.setId(testResultEntity.getId());
        testResultDto.setTitle(testResultEntity.getTitle());
        testResultDto.setLocation(testResultEntity.getLocation());
        testResultDto.setNote(testResultEntity.getNote());
        testResultDto.setDateOfTheTest(testResultEntity.getDateOfTheTest());
        testResultDto.setUserId(testResultEntity.getUserById().getId());

        var testTypeEntity = testResultEntity.getTestTypeById();
        testResultDto.setTestType(testTypeService.testTypeToDto(testTypeEntity));

        var files = testResultEntity.getFilesById();
        testResultDto.setFiles(fileService.filesToDtos(files));

        var testResultItems = testResultEntity.getTestResultItemsById();
        testResultDto.setTestResultItems(testResultItemService.testResultItemsToDtos(testResultItems));

        return testResultDto;
    }

    @Override
    @Transactional
    public int deleteTestResult(UserDto user, Integer testResultId) throws BlobStorageException, EntityNotFoundException {
        TestResultEntity testResult = testResultRepository.findById(testResultId);
        if(testResult != null){
            if(testResult.getUserById().getId().equals(user.getId())){
                Collection<TestResultItemEntity> testResultItems = testResultItemRepository.findAllByTestResultById(testResult);
                for(TestResultItemEntity testResultItem : testResultItems){
                    testResultItemService.deleteTestResultItem(testResultItem.getId());
                }
                List<FileEntity> files = fileRepository.findByTestResultId(testResultId);
                for(FileEntity file : files){
                    fileService.deleteFile(file.getId());
                }
                if(files.isEmpty()){
                    testResultRepository.deleteById(testResultId);
                    return Const.testResultDeletionSuccess;
                }
                else{
                    return Const.testResultFileDeletionError;
                }
            }
            return Const.testResultDeletionError;
        }
        return Const.testResultDoesNotExists;
    }

    @Override
    public List<GetTestResultDto> getTestResultsSorted(List<GetTestResultDto> testResults, String sortType){
        List<GetTestResultDto> sortedResults = chooseSort(sortType, testResults);
        return sortedResults;
    }

    @Override
    public List<GetTestResultDto> sortByToday(List<GetTestResultDto> testResults) {
        List<GetTestResultDto> sorted = new ArrayList<>();
        for (GetTestResultDto testResult: testResults
        ) {
            if(testResult.getDateOfTheTest().toLocalDate().equals(LocalDate.now()))
                sorted.add(testResult);
        }
        return sorted;
    }

    @Override
    public List<GetTestResultDto> sortByLastWeek(List<GetTestResultDto> testResults) {
        List<GetTestResultDto> sorted = new ArrayList<>();
        for (GetTestResultDto testResult: testResults
        ) {
            if(testResult.getDateOfTheTest().toLocalDate().isAfter(LocalDate.now().minusDays(7))){
                sorted.add(testResult);
            }

        }
        return sorted;
    }

    @Override
    public List<GetTestResultDto> sortByLastMonth(List<GetTestResultDto> testResults) {
        List<GetTestResultDto> sorted = new ArrayList<>();
        for (GetTestResultDto testResult: testResults
        ) {
            if(testResult.getDateOfTheTest().toLocalDate().isAfter(LocalDate.now().minusDays(30)))
                sorted.add(testResult);
        }
        return sorted;
    }

    @Override
    public List<GetTestResultDto> sortByLastYear(List<GetTestResultDto> testResults) {
        List<GetTestResultDto> sorted = new ArrayList<>();
        for (GetTestResultDto testResult: testResults
        ) {
            if(testResult.getDateOfTheTest().toLocalDate().isAfter(LocalDate.now().minusDays(365)))
                sorted.add(testResult);
        }
        return sorted;
    }

    @Override
    public List<GetTestResultDto> sortByPast(List<GetTestResultDto> testResults) {
        List<GetTestResultDto> sorted = new ArrayList<>();
        for (GetTestResultDto testResult: testResults
        ) {
            if(testResult.getDateOfTheTest().toLocalDate().isBefore(LocalDate.now()) ||
                    testResult.getDateOfTheTest().toLocalDate().equals(LocalDate.now()))
                sorted.add(testResult);
        }
        return sorted;
    }

    private List<GetTestResultDto> chooseSort(String sortType,  List<GetTestResultDto> testResults) {
        List<GetTestResultDto> sortedEvents = null;
        if(sortType.equals(SortType.Today.toString())) sortedEvents = sortByToday(testResults);
        if(sortType.equals(SortType.LastWeek.toString())) sortedEvents = sortByLastWeek(testResults);
        if(sortType.equals(SortType.LastMonth.toString())) sortedEvents = sortByLastMonth(testResults);
        if(sortType.equals(SortType.LastYear.toString())) sortedEvents = sortByLastYear(testResults);
        if(sortType.equals(SortType.Past.toString())) sortedEvents = sortByPast(testResults);
        return sortedEvents;
    }

}
