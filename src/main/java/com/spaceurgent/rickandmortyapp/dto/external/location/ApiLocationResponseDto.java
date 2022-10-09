package com.spaceurgent.rickandmortyapp.dto.external.location;

import com.spaceurgent.rickandmortyapp.dto.external.ApiInfoDto;
import lombok.Data;

@Data
public class ApiLocationResponseDto {
    private ApiInfoDto info;
    private ApiLocationDto[] results;
}
