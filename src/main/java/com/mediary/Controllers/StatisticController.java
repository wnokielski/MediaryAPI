package com.mediary.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.DTOs.Request.AddStatisticDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Services.Interfaces.IStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    IStatisticService statisticService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> addStatistics(@PathVariable Integer userId,
            @RequestBody List<AddStatisticDto> statistics) {
        try {
            statisticService.addStatistics(statistics, userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping("/{userId}/{statisticTypeId}")
    public ResponseEntity<List<GetStatisticDto>> getStatisticsByUserAndStatisticType(@PathVariable Integer userId,
            @PathVariable Integer statisticTypeId) {
        ArrayList<GetStatisticDto> statisticDtos;
        try {
            statisticDtos = (ArrayList<GetStatisticDto>) statisticService.getStatisticsByUserAndStatisticType(userId,
                    statisticTypeId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(statisticDtos, HttpStatus.OK);
    }

}
