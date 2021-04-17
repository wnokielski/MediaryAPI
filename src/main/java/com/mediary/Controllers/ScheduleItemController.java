package com.mediary.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.DTOs.Request.AddScheduleItemDto;
import com.mediary.Models.DTOs.Response.GetScheduleItemDto;
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
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/schedule")
public class ScheduleItemController {

    @Autowired
    IScheduleItemService scheduleItemService;

    @ResponseBody
    @GetMapping("/{userId}")
    public ResponseEntity<List<GetScheduleItemDto>> getScheduleItemsByUser(@PathVariable Integer userId) {
        ArrayList<GetScheduleItemDto> scheduleItemDtos;
        try {
            scheduleItemDtos = (ArrayList<GetScheduleItemDto>) scheduleItemService.getScheduleItemsByUser(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (scheduleItemDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(scheduleItemDtos, HttpStatus.OK);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addScheduleItems(@PathVariable Integer userId,
            @RequestBody List<AddScheduleItemDto> scheduleItems) {
        try {
            scheduleItemService.addScheduleItems(scheduleItems, userId);
        } catch (Exception e) {
            log.warn(e.getStackTrace().toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
