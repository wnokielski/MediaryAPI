package com.mediary.Controllers;

import java.util.List;

import com.mediary.Models.Dtos.Response.GetStatisticTypeDto;
import com.mediary.Services.Interfaces.IStatisticTypeService;

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
@RequestMapping("/api/statistic/type")
public class StatisticTypeController {

    @Autowired
    IStatisticTypeService statisticTypeService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<GetStatisticTypeDto>> getAllStatisticTypes() {
        var statisticTypeDtos = statisticTypeService.getAllStatisticTypes();
        return new ResponseEntity<List<GetStatisticTypeDto>>(statisticTypeDtos, HttpStatus.OK);
    }

}
