package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.Dtos.Request.AddTestResultDto;
import com.mediary.Models.Dtos.Response.GetTestResultDto;
import com.mediary.Services.Interfaces.ITestResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/test/result")
public class TestResultController {

    @Autowired
    ITestResultService testResultService;

    @ResponseBody
    @GetMapping("/{userId}")
    public ResponseEntity<List<GetTestResultDto>> getUserTestResults(@PathVariable Integer userId) {
        var testResultDtos = testResultService.getTestResultsByUser(userId);
        if (testResultDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<GetTestResultDto>>(testResultDtos, HttpStatus.OK);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addTestResult(@PathVariable Integer userId, @RequestBody AddTestResultDto testResultDto) {
        try {
            testResultService.addTestResult(testResultDto, userId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
