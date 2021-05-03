package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddScheduleItemDto;
import com.mediary.Models.DTOs.Response.GetScheduleItemDto;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IScheduleItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/schedule")
public class ScheduleItemController {

    @Autowired
    IScheduleItemService scheduleItemService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<GetScheduleItemDto>> getScheduleItemsByUser(
            @RequestHeader("Authorization") String authHeader) throws EntityNotFoundException {

        var scheduleItemDtos = scheduleItemService.getScheduleItemsByAuthHeader(authHeader);
        if (scheduleItemDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(scheduleItemDtos, HttpStatus.OK);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addScheduleItems(@RequestHeader("Authorization") String authHeader,
            @RequestBody List<AddScheduleItemDto> scheduleItems)
            throws EntityNotFoundException, IncorrectFieldException {
        scheduleItemService.addScheduleItemsByAuthHeader(scheduleItems, authHeader);
    }
}
