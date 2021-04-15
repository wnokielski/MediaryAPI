package com.mediary.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.Dtos.Request.AddStatisticRequestDto;
import com.mediary.Models.Dtos.Response.GetStatisticDto;
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

@CrossOrigin
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    IStatisticService statisticService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> addStatistics(int userId, @RequestBody List<AddStatisticRequestDto> statistics) {
        try {
            statisticService.addStatistics(statistics, userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping("/{userId}/{statisticTypeId}")
    public ResponseEntity<List<GetStatisticDto>> getStatisticsByUserAndStatisticType(Integer userId,
            Integer statisticTypeId) {
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
