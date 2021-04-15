package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.Dtos.Response.GetTestTypeDto;
import com.mediary.Services.Implementation.TestTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/test/type")
public class TestTypeController {

    @Autowired
    TestTypeService testTypeService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<GetTestTypeDto>> getAllTestTypes() {
        var testTypes = testTypeService.getAllTestTypes();
        if (testTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GetTestTypeDto>>(testTypes, HttpStatus.OK);
    }

}
