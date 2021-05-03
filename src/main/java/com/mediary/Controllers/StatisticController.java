package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddStatisticDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    IStatisticService statisticService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addStatistics(@RequestHeader("Authorization") String authHeader,
            @RequestBody List<AddStatisticDto> statistics) throws EntityNotFoundException, IncorrectFieldException {

        statisticService.addStatistcsByAuthHeader(statistics, authHeader);

    }

    @ResponseBody
    @GetMapping("/{statisticTypeId}")
    public ResponseEntity<List<GetStatisticDto>> getStatisticsByUserAndStatisticType(
            @RequestHeader("Authorization") String authHeader, @PathVariable Integer statisticTypeId)
            throws EntityNotFoundException {
        var statisticDtos = statisticService.getStatisticsByAuthHeaderAndStatisticType(authHeader, statisticTypeId);
        if (statisticDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(statisticDtos, HttpStatus.OK);
        }
    }

}
