package com.spaceurgent.rickandmortyapp.dto.external.charcter;

import lombok.Data;

@Data
public class ApiMovieCharacterDto {
    private Long id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private ApiOriginDto origin;
    private ApiOriginDto location;
    private String image;
}
