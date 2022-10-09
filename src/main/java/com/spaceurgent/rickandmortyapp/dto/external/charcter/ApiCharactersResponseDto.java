package com.spaceurgent.rickandmortyapp.dto.external.charcter;

import com.spaceurgent.rickandmortyapp.dto.external.ApiInfoDto;
import lombok.Data;

@Data
public class ApiCharactersResponseDto {
    private ApiInfoDto info;
    private ApiMovieCharacterDto[] results;
}
