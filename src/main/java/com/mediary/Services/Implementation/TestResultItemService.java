package com.mediary.Services.Implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediary.Models.DTOs.Request.AddTestResultItemDto;
import com.mediary.Models.DTOs.Response.GetTestResultItemDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;
import com.mediary.Repositories.TestResultItemRepository;
import com.mediary.Repositories.TestResultRepository;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.ITestResultItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestResultItemService implements ITestResultItemService {

    @Autowired
    TestResultItemRepository testResultItemRepository;

    @Autowired
    TestResultRepository testResultRepostiory;

    @Override
    public void addTestResultItems(Collection<AddTestResultItemDto> testResultItemDtos, TestResultEntity testResult)
            throws IncorrectFieldException {
        for (var testResultItemDto : testResultItemDtos) {
            addTestResultItem(testResultItemDto, testResult);
        }
    }

    @Override
    public void addTestResultItem(AddTestResultItemDto testResultItemDto, TestResultEntity testResult)
            throws IncorrectFieldException {

        if (testResultItemDto.getName().length() > 40 || testResultItemDto.getName() == "") {
            throw new IncorrectFieldException("Name field is incorrect");
        } else if (testResultItemDto.getValue().length() > 50 || testResultItemDto.getValue() == "") {
            throw new IncorrectFieldException("Value field is incorrect");
        } else if (testResultItemDto.getUnit().length() > 10) {
            throw new IncorrectFieldException("Unit field is incorrect");
        } else {
            TestResultItemEntity testResultItem = new TestResultItemEntity();
            testResultItem.setName(testResultItemDto.getName());
            testResultItem.setValue(testResultItemDto.getValue());
            testResultItem.setUnit(testResultItemDto.getUnit());
            testResultItem.setTestresultByTestresultid(testResult);
            testResultItemRepository.save(testResultItem);
        }
    }

    @Override
    public AddTestResultItemDto getJson(String testResultItem) {
        AddTestResultItemDto testResultItemDto = new AddTestResultItemDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            testResultItemDto = objectMapper.readValue(testResultItem, AddTestResultItemDto.class);
        } catch (IOException e) {
            log.warn("Mapping error");
        }
        return testResultItemDto;
    }

    @Override
    public List<GetTestResultItemDto> getAllByTestResultId(Integer testResultId) throws EntityNotFoundException {
        if (testResultRepostiory.findById(testResultId) == null) {
            throw new EntityNotFoundException("Test result with specified ID doesn't exist");
        } else {
            var testResultItems = testResultItemRepository.findByTestResultId(testResultId);
            List<GetTestResultItemDto> testResultItemDtos = new ArrayList<GetTestResultItemDto>();
            for (TestResultItemEntity testResultItemEntity : testResultItems) {
                var testResultItemDto = testResultItemToDto(testResultItemEntity);
                testResultItemDtos.add(testResultItemDto);
            }
            return testResultItemDtos;
        }

    }

    @Override
    public Collection<GetTestResultItemDto> testResultItemsToDtos(Collection<TestResultItemEntity> testResultItems) {
        Collection<GetTestResultItemDto> testResultItemDtos = new ArrayList<GetTestResultItemDto>();
        for (TestResultItemEntity testResultItemEntity : testResultItems) {
            var testResultItemDto = testResultItemToDto(testResultItemEntity);
            testResultItemDtos.add(testResultItemDto);
        }
        return testResultItemDtos;
    }

    @Override
    public GetTestResultItemDto testResultItemToDto(TestResultItemEntity testResultItem) {
        GetTestResultItemDto testResultItemDto = new GetTestResultItemDto();
        testResultItemDto.setId(testResultItem.getId());
        testResultItemDto.setName(testResultItem.getName());
        testResultItemDto.setUnit(testResultItem.getUnit());
        testResultItemDto.setValue(testResultItem.getValue());
        return testResultItemDto;
    }
}
