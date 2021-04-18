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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @GetMapping("/{userId}")
    public ResponseEntity<List<GetScheduleItemDto>> getScheduleItemsByUser(@PathVariable Integer userId)
            throws EntityNotFoundException {

        var scheduleItemDtos = scheduleItemService.getScheduleItemsByUser(userId);
        if (scheduleItemDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(scheduleItemDtos, HttpStatus.OK);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}")
    public void addScheduleItems(@PathVariable Integer userId, @RequestBody List<AddScheduleItemDto> scheduleItems)
            throws EntityNotFoundException, IncorrectFieldException {
        scheduleItemService.addScheduleItems(scheduleItems, userId);
    }
}
