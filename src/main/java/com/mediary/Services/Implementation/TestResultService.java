package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.Dtos.Request.AddFileDto;
import com.mediary.Models.Dtos.Request.AddTestResultDto;
import com.mediary.Models.Dtos.Response.GetTestResultDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Repositories.TestResultRepository;
import com.mediary.Repositories.TestTypeRepository;
import com.mediary.Repositories.UserRepository;
import com.mediary.Services.Interfaces.IFileService;
import com.mediary.Services.Interfaces.ITestResultItemService;
import com.mediary.Services.Interfaces.ITestResultService;
import com.mediary.Services.Interfaces.ITestTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    public void addTestResult(AddTestResultDto testResultDto, Integer userId) throws Exception {
        if (testResultDto.getTitle().length() > 30 || testResultDto.getTitle() == "") {
            throw new Exception("Title field is incorrect");
        } else if (testResultDto.getNote().length() > 200) {
            throw new Exception("Note is too long");
        } else if (testResultDto.getDateofthetest() == null) {
            throw new Exception("Date of the test is required");
        } else {
            TestResultEntity testResult = new TestResultEntity();
            testResult.setTitle(testResultDto.getTitle());
            testResult.setNote(testResultDto.getNote());
            testResult.setDateofthetest(testResultDto.getDateofthetest());

            var testType = testTypeRepository.findById(testResultDto.getTestTypeId());
            testResult.setTesttypeByTesttypeid(testType);

            var user = userRepository.findById(userId);
            testResult.setUserByUserid(user);

            testResultRepository.save(testResult);

            for (AddFileDto fileDto : testResultDto.getFiles()) {
                fileService.uploadFile(fileDto.getFile(), userId, testResult);
            }

            testResultItemService.addTestResultItems(testResultDto.getTestResultItems(), testResult);
        }
    }

    @Override
    public List<GetTestResultDto> getUsersTestResults(Integer userId) {
        var testResultEntities = testResultRepository.findByUserByUserid(userId);
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
