package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.Dtos.Response.GetScheduleItemTypeDto;
import com.mediary.Services.Interfaces.IScheduleItemTypeService;

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
@RequestMapping("/api/schedule/type")
public class ScheduleItemTypeController {
    @Autowired
    IScheduleItemTypeService scheduleItemTypeService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<GetScheduleItemTypeDto>> getAllScheduleItemTypes() {
        var scheduleItemTypes = scheduleItemTypeService.getAllScheduleItemTypes();
        if (scheduleItemTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GetScheduleItemTypeDto>>(scheduleItemTypes, HttpStatus.OK);
    }
}
