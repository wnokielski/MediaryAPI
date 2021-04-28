package com.mediary.Services.Implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediary.Models.DTOs.Request.AddTestResultDto;
import com.mediary.Models.DTOs.Response.GetTestResultDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Repositories.TestResultRepository;
import com.mediary.Repositories.TestTypeRepository;
import com.mediary.Repositories.UserRepository;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IFileService;
import com.mediary.Services.Interfaces.ITestResultItemService;
import com.mediary.Services.Interfaces.ITestResultService;
import com.mediary.Services.Interfaces.ITestTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    UserRepository userRepository;

    @Autowired
    IFileService fileService;

    @Autowired
    ITestTypeService testTypeService;

    @Autowired
    ITestResultItemService testResultItemService;

    @Override
    public void addTestResult(AddTestResultDto testResultDto, MultipartFile[] files, Integer userId)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException {

        if (testResultDto.getTitle().length() > 30 || testResultDto.getTitle() == "") {
            throw new IncorrectFieldException("Title field is incorrect");
        } else if (testResultDto.getNote().length() > 200) {
            throw new IncorrectFieldException("Note is too long");
        } else if (testResultDto.getDateofthetest() == null) {
            throw new IncorrectFieldException("Date of the test is required");
        } else {
            TestResultEntity testResultEntity = new TestResultEntity();
            testResultEntity.setTitle(testResultDto.getTitle());
            testResultEntity.setNote(testResultDto.getNote());
            testResultEntity.setDateofthetest(testResultDto.getDateofthetest());

            var testType = testTypeRepository.findById(testResultDto.getTestTypeId());
            if (testType != null) {
                testResultEntity.setTesttypeByTesttypeid(testType);
            } else {
                throw new EntityNotFoundException("Test Type with specified id doesn't exist");
            }

            var user = userRepository.findByUserId(userId);
            if (user != null) {
                testResultEntity.setUserByUserid(user);
            } else {
                throw new EntityNotFoundException("User with specified id doesn't exist");
            }

            for (MultipartFile file : files) {
                fileService.uploadFile(file, userId, testResultEntity);
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
    public List<GetTestResultDto> getTestResultsByUser(Integer userId) throws EntityNotFoundException {
        if (userRepository.findByUserId(userId) != null) {
            var testResultEntities = testResultRepository.findByUser(userId);
            List<GetTestResultDto> testResultDtos = testResultsToDtos(testResultEntities);
            return testResultDtos;
        } else {
            throw new EntityNotFoundException("User with specified id doesn't exist");
        }

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
        testResultDto.setNote(testResultEntity.getNote());
        testResultDto.setDateOfTheTest(testResultEntity.getDateofthetest());
        testResultDto.setUserId(testResultEntity.getUserByUserid().getId());

        var testTypeEntity = testResultEntity.getTesttypeByTesttypeid();
        testResultDto.setTestType(testTypeService.testTypeToDto(testTypeEntity));

        var files = testResultEntity.getFilesById();
        testResultDto.setFiles(fileService.filesToDtos(files));

        var testResultItems = testResultEntity.getTestresultitemsById();
        testResultDto.setTestResultItems(testResultItemService.testResultItemsToDtos(testResultItems));

        return testResultDto;
    }
}