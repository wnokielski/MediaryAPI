package com.mediary.Controllers;

import java.util.List;
import java.util.Map;

import com.mediary.Models.DTOs.Request.AddStatisticDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/byDate/{statisticTypeId}/{dateFrom}/{dateTo}")
    public ResponseEntity<List<GetStatisticDto>> getStatisticsByUserAndStatisticTypeAndDate (
            @RequestHeader("Authorization") String authHeader, @PathVariable Integer statisticTypeId,
            @PathVariable String dateFrom, @PathVariable String dateTo) throws EntityNotFoundException{
        var statisticDtos = statisticService.getStatisticsByAuthHeaderAndStatisticTypeAndDate(authHeader, statisticTypeId, dateFrom, dateTo);
        if (statisticDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(statisticDtos, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{statisticId}")
    public ResponseEntity<?> deleteStatisticByUserAndStatisticId(
            @RequestHeader("Authorization") String authHeader, @PathVariable Integer statisticId)
            throws EntityNotFoundException {
        var qty = statisticService.deleteStatisticByAuthHeaderAndStatisticId(authHeader, statisticId);
        if (qty == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PatchMapping("/{statisticId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatistic(@RequestBody Map<String, Object> updates,
                                @RequestHeader("Authorization") String authHeader,
                                @PathVariable Integer statisticId) throws EntityNotFoundException {
        statisticService.updateStatisticByAuthHeaderAndStatisticId(authHeader, statisticId, updates);
    }
}
