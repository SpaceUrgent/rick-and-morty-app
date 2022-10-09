package com.spaceurgent.rickandmortyapp.dto.external;

import lombok.Data;

@Data
public abstract class ApiResponseDto<T> {
    ApiInfoDto info;
    T[] results;
}
