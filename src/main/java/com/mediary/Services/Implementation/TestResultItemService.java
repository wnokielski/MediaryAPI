package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mediary.Models.Dtos.Request.AddTestResultItemDto;
import com.mediary.Models.Dtos.Response.GetTestResultItemDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;
import com.mediary.Repositories.TestResultItemRepository;
import com.mediary.Services.Interfaces.ITestResultItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestResultItemService implements ITestResultItemService {

    @Autowired
    TestResultItemRepository testResultItemRepository;

    @Override
    public void addTestResultItems(Collection<AddTestResultItemDto> testResultItemDtos, TestResultEntity testResult) {
        for (var testResultItemDto : testResultItemDtos) {
            addTestResultItem(testResultItemDto, testResult);
        }
    }

    @Override
    public void addTestResultItem(AddTestResultItemDto testResultItemDto, TestResultEntity testResult) {
        var testResultItem = new TestResultItemEntity();
        testResultItem.setName(testResultItemDto.getName());
        testResultItem.setValue(testResultItemDto.getValue());
        testResultItem.setUnit(testResultItemDto.getUnit());
        testResultItem.setTestresultByTestresultid(testResult);
        testResultItemRepository.save(testResultItem);
        testResultItemRepository.flush();
    }

    @Override
    public List<GetTestResultItemDto> getAllByTestResultId(Integer testResultId) {
        var testResultItems = testResultItemRepository.findByTestResultId(testResultId);
        List<GetTestResultItemDto> testResultItemDtos = new ArrayList<GetTestResultItemDto>();
        for (TestResultItemEntity testResultItemEntity : testResultItems) {
            var testResultItemDto = testResultItemToDto(testResultItemEntity);
            testResultItemDtos.add(testResultItemDto);
        }
        return testResultItemDtos;
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
