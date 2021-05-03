package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddTestResultDto;
import com.mediary.Models.DTOs.Response.GetTestResultDto;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.ITestResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/test/result")
public class TestResultController {

    @Autowired
    ITestResultService testResultService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<GetTestResultDto>> getUserTestResults(@RequestHeader String authHeader)
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
    public void addTestResult(@RequestHeader String authHeader, @RequestPart(required = false) MultipartFile[] files,
            @RequestPart AddTestResultDto testResult)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException {

        testResultService.addTestResultByAuthHeader(testResult, files, authHeader);
    }

}
