package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddScheduleItemDto;
import com.mediary.Models.DTOs.Request.ScheduleItemUpdateDto;
import com.mediary.Models.DTOs.Request.UserUpdateDto;
import com.mediary.Models.DTOs.Response.GetScheduleItemDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Models.DTOs.UserDto;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.EnumConversionException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Exceptions.ScheduleItem.ScheduleItemDeletionError;
import com.mediary.Services.Exceptions.ScheduleItem.ScheduleItemDoesNotBelongToThisUser;
import com.mediary.Services.Exceptions.ScheduleItem.ScheduleItemDoesNotExist;
import com.mediary.Services.Exceptions.User.EmailAlreadyUsedException;
import com.mediary.Services.Exceptions.User.EmailToLongException;
import com.mediary.Services.Exceptions.User.FullNameToLongException;
import com.mediary.Services.Exceptions.User.UserDoesNotExist;
import com.mediary.Services.Implementation.UserService;
import com.mediary.Services.Interfaces.IScheduleItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/schedule")
public class ScheduleItemController {

    @Autowired
    IScheduleItemService scheduleItemService;

    @Autowired
    UserService userService;

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

    @DeleteMapping("/{scheduleItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteScheduleItem(@RequestHeader("Authorization") String authHeader, @PathVariable ("scheduleItemId") Integer scheduleItemId)
            throws ScheduleItemDeletionError, ScheduleItemDoesNotExist, EntityNotFoundException {
        UserDto user = userService.getUserDetails(authHeader);
        int result = scheduleItemService.deleteScheduleItem(user, scheduleItemId);
        if (result == Const.scheduleItemDeletionError)
            throw new ScheduleItemDeletionError("Schedule item doesn't belong to this user!");
        if (result == Const.scheduleItemDoesNotExist)
            throw new ScheduleItemDoesNotExist("Schedule item does not exist!");
    }

    @GetMapping("/byDate/{dateFrom}/{dateTo}")
    public ResponseEntity<List<GetScheduleItemDto>> getScheduleItemByUserAndDate (
            @RequestHeader("Authorization") String authHeader, @PathVariable String dateFrom, @PathVariable String dateTo) throws EntityNotFoundException{
        List<GetScheduleItemDto> scheduleItemDtos = scheduleItemService.getScheduleItemByAuthHeadeAndDate(authHeader, dateFrom, dateTo);
        if (scheduleItemDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(scheduleItemDtos, HttpStatus.OK);
        }
    }

    @PutMapping("/{scheduleItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateScheduleItem(@RequestHeader("Authorization") String authHeader, @RequestBody ScheduleItemUpdateDto scheduleItemUpdateDto, @PathVariable ("scheduleItemId") Integer scheduleItemId) throws EntityNotFoundException, ScheduleItemDoesNotBelongToThisUser, IncorrectFieldException {
        UserDto user = userService.getUserDetails(authHeader);
        scheduleItemService.updateScheduleItem(scheduleItemUpdateDto, user, scheduleItemId);
    }
}
