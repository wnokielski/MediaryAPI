package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddMedicalRecordDto;
import com.mediary.Models.DTOs.Response.GetMedicalRecordDto;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IMedicalRecordService;

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
@RequestMapping("/api/record")
public class MedicalRecordController {

    @Autowired
    IMedicalRecordService medicalRecordService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<GetMedicalRecordDto>> getUserMedicalRecords(@RequestHeader("Authorization") String authHeader)
            throws EntityNotFoundException {
        var medicalRecordDtos = medicalRecordService.getMedicalRecordsByAuthHeader(authHeader);
        if (medicalRecordDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<GetMedicalRecordDto>>(medicalRecordDtos, HttpStatus.OK);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addMedicalRecord(@RequestHeader("Authorization") String authHeader,
            @RequestPart(required = false) MultipartFile[] files, @RequestPart AddMedicalRecordDto medicalRecord)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException {

        medicalRecordService.addMedicalRecordByAuthHeader(medicalRecord, files, authHeader);
    }

}
