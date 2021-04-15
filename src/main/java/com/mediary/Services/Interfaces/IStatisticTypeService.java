package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.Dtos.Response.GetStatisticTypeDto;

public interface IStatisticTypeService {

    List<GetStatisticTypeDto> getAllStatisticTypes();
}
