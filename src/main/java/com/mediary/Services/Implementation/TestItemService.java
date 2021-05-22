package com.mediary.Services.Implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediary.Models.DTOs.Request.AddTestItemDto;
import com.mediary.Models.DTOs.Response.GetTestItemDto;
import com.mediary.Models.Entities.MedicalRecordEntity;
import com.mediary.Models.Entities.TestItemEntity;
import com.mediary.Repositories.TestItemRepository;
import com.mediary.Repositories.MedicalRecordRepository;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.ITestItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestItemService implements ITestItemService {

    @Autowired
    TestItemRepository testItemRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepostiory;

    @Override
    public void addTestItems(Collection<AddTestItemDto> testItemDtos, MedicalRecordEntity medicalRecord)
            throws IncorrectFieldException {
        for (var testItemDto : testItemDtos) {
            addTestItem(testItemDto, medicalRecord);
        }
    }

    @Override
    public void addTestItem(AddTestItemDto testItemDto, MedicalRecordEntity medicalRecord)
            throws IncorrectFieldException {

        if (testItemDto.getName().length() > 40 || testItemDto.getName() == "") {
            throw new IncorrectFieldException("Name field is incorrect");
        } else if (testItemDto.getValue().length() > 50 || testItemDto.getValue() == "") {
            throw new IncorrectFieldException("Value field is incorrect");
        } else if (testItemDto.getUnit().length() > 10) {
            throw new IncorrectFieldException("Unit field is incorrect");
        } else {
            TestItemEntity testItem = new TestItemEntity();
            testItem.setName(testItemDto.getName());
            testItem.setValue(testItemDto.getValue());
            testItem.setUnit(testItemDto.getUnit());
            testItem.setMedicalRecordById(medicalRecord);
            testItemRepository.save(testItem);
        }
    }

    @Override
    public AddTestItemDto getJson(String testItem) {
        AddTestItemDto testItemDto = new AddTestItemDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            testItemDto = objectMapper.readValue(testItem, AddTestItemDto.class);
        } catch (IOException e) {
            log.warn("Mapping error");
        }
        return testItemDto;
    }

    @Override
    public List<GetTestItemDto> getAllByMedicalRecordId(Integer medicalRecordId) throws EntityNotFoundException {
        if (medicalRecordRepostiory.findById(medicalRecordId) == null) {
            throw new EntityNotFoundException("Medical Record with specified ID doesn't exist");
        } else {
            var testItems = testItemRepository.findByMedicalRecordId(medicalRecordId);
            List<GetTestItemDto> testItemDtos = new ArrayList<GetTestItemDto>();
            for (TestItemEntity testItemEntity : testItems) {
                var testItemDto = testItemToDto(testItemEntity);
                testItemDtos.add(testItemDto);
            }
            return testItemDtos;
        }

    }

    @Override
    public Collection<GetTestItemDto> testItemsToDtos(Collection<TestItemEntity> testItems) {
        Collection<GetTestItemDto> testItemDtos = new ArrayList<GetTestItemDto>();
        for (TestItemEntity testItemEntity : testItems) {
            var testItemDto = testItemToDto(testItemEntity);
            testItemDtos.add(testItemDto);
        }
        return testItemDtos;
    }

    @Override
    public GetTestItemDto testItemToDto(TestItemEntity testItem) {
        GetTestItemDto testItemDto = new GetTestItemDto();
        testItemDto.setId(testItem.getId());
        testItemDto.setName(testItem.getName());
        testItemDto.setUnit(testItem.getUnit());
        testItemDto.setValue(testItem.getValue());
        return testItemDto;
    }
}
