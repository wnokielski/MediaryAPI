package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddTestResultDto;
import com.mediary.Models.DTOs.Request.AddTestResultItemDto;
import com.mediary.Models.DTOs.Request.UpdateTestResultDto;
import com.mediary.Models.DTOs.Request.UpdateTestResultItemDto;
import com.mediary.Models.DTOs.Response.GetTestResultDto;
import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityDoesNotBelongToUser;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Exceptions.TestResult.TestResultFileDeletionError;
import com.mediary.Services.Interfaces.ITestResultService;

import com.mediary.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/test/result")
public class TestResultController {

    @Autowired
    ITestResultService testResultService;

    @Autowired
    IUserService userService;


    @ResponseBody
    @GetMapping
    public ResponseEntity<List<GetTestResultDto>> getUserTestResults(@RequestHeader("Authorization") String authHeader)
            throws EntityNotFoundException {
        var testResultDtos = testResultService.getTestResultsByAuthHeader(authHeader);
        if (testResultDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<GetTestResultDto>>(testResultDtos, HttpStatus.OK);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addTestResult(@RequestHeader("Authorization") String authHeader,
            @RequestPart(required = false) MultipartFile[] files, @RequestPart AddTestResultDto testResult)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException {

        testResultService.addTestResultByAuthHeader(testResult, files, authHeader);
    }

    @DeleteMapping("/{testResultId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTestResult(@RequestHeader("Authorization") String authHeader, @PathVariable ("testResultId") Integer testResultId)
            throws EntityNotFoundException, BlobStorageException, EntityDoesNotBelongToUser, TestResultFileDeletionError {
        UserDto user = userService.getUserDetails(authHeader);
        int result = testResultService.deleteTestResult(user, testResultId);
        if (result == Const.testResultDeletionError)
            throw new EntityDoesNotBelongToUser("Test result doesn't belong to this user!");
        if (result == Const.testResultDoesNotExists)
            throw new EntityNotFoundException("Test result does not exist!");
        if (result == Const.testResultFileDeletionError)
            throw new TestResultFileDeletionError("File deletion error");
    }

    @GetMapping("/{sortType}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GetTestResultDto>> getTestResults(@RequestHeader("Authorization") String authHeader, @PathVariable ("sortType") String sortType) throws EntityNotFoundException {
        var testResultDtos = testResultService.getTestResultsByAuthHeader(authHeader);
        if (testResultDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<GetTestResultDto> sortedTestResultDtos = testResultService.getTestResultsSorted(testResultDtos, sortType);
            if(sortedTestResultDtos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else{
                return new ResponseEntity<>(sortedTestResultDtos, HttpStatus.OK);
            }
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{testResultId}")
    public void updateTestResultById(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateTestResultDto testResult, @PathVariable ("testResultId") Integer testResultId)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException, EntityDoesNotBelongToUser {
        testResultService.updateTestResultById(testResult, authHeader, testResultId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/item/{testResultItemId}")
    public void updateTestResultItemById(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateTestResultItemDto testResultItem, @PathVariable ("testResultItemId") Integer testResultItemId)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException, EntityDoesNotBelongToUser {
        testResultService.updateTestResultItemById(testResultItem, authHeader, testResultItemId);
    }

    
}
