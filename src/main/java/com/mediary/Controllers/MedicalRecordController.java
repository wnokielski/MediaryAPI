package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddMedicalRecordDto;
import com.mediary.Models.DTOs.Request.UpdateMedicalRecordDto;
import com.mediary.Models.DTOs.Request.UpdateTestItemDto;
import com.mediary.Models.DTOs.Response.GetMedicalRecordDto;
import com.mediary.Models.DTOs.Response.GetScheduleItemDto;
import com.mediary.Models.DTOs.UserDto;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.*;
import com.mediary.Services.Exceptions.MedicalRecord.MedicalRecordFileDeletionError;
import com.mediary.Services.Interfaces.IMedicalRecordService;

import com.mediary.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/record")
public class MedicalRecordController {

    @Autowired
    IMedicalRecordService medicalRecordService;

    @Autowired
    IUserService userService;

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

    @DeleteMapping("/{medicalRecordId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMedicalRecord(@RequestHeader("Authorization") String authHeader, @PathVariable ("medicalRecordId") Integer medicalRecordId)
            throws EntityNotFoundException, BlobStorageException, EntityDoesNotBelongToUser, MedicalRecordFileDeletionError {
        UserDto user = userService.getUserDetails(authHeader);
        int result = medicalRecordService.deleteMedicalRecord(user, medicalRecordId);
        if (result == Const.medicalRecordDeletionError)
            throw new EntityDoesNotBelongToUser("Medical Record doesn't belong to this user!");
        if (result == Const.medicalRecordDoesNotExists)
            throw new EntityNotFoundException("Medical Record does not exist!");
        if (result == Const.medicalRecordFileDeletionError)
            throw new MedicalRecordFileDeletionError("File deletion error");
    }

    @GetMapping("/{sortType}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GetMedicalRecordDto>> getMedicalRecords(@RequestHeader("Authorization") String authHeader, @PathVariable ("sortType") String sortType) throws EntityNotFoundException {
        var medicalRecordDtos = medicalRecordService.getMedicalRecordsByAuthHeader(authHeader);
        if (medicalRecordDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<GetMedicalRecordDto> sortedMedicalRecordDtos = medicalRecordService.getMedicalRecordsSorted(medicalRecordDtos, sortType);
            if(sortedMedicalRecordDtos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else{
                return new ResponseEntity<>(sortedMedicalRecordDtos, HttpStatus.OK);
            }
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{medicalRecordId}")
    public void updateMedicalRecordById(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateMedicalRecordDto medicalRecord, @PathVariable ("medicalRecordId") Integer medicalRecordId)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException, EntityDoesNotBelongToUser, EnumConversionException {
        medicalRecordService.updateMedicalRecordById(medicalRecord, authHeader, medicalRecordId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/item/{medicalRecordItemId}")
    public void updateMedicalRecordItemById(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateTestItemDto medicalRecordItem, @PathVariable ("medicalRecordItemId") Integer medicalRecordItemId)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException, EntityDoesNotBelongToUser {
        medicalRecordService.updateTestItemById(medicalRecordItem, authHeader, medicalRecordItemId);
    }

    @GetMapping("/byDate/{dateFrom}/{dateTo}")
    public ResponseEntity<List<GetMedicalRecordDto>> getMedicalRecordByUserAndDate (
            @RequestHeader("Authorization") String authHeader, @PathVariable String dateFrom, @PathVariable String dateTo) throws EntityNotFoundException {
        List<GetMedicalRecordDto> medicalRecordDtos = medicalRecordService.getScheduleItemByAuthHeaderAndDate(authHeader, dateFrom, dateTo);
        if (medicalRecordDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(medicalRecordDtos, HttpStatus.OK);
        }
    }

}
