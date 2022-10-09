package com.spaceurgent.rickandmortyapp.dto.external.episode;

import com.spaceurgent.rickandmortyapp.dto.external.ApiInfoDto;
import lombok.Data;

@Data
public class ApiEpisodeResponseDto {
    ApiInfoDto info;
    ApiEpisodeDto[] results;
}
