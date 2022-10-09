package com.spaceurgent.rickandmortyapp.dto.external.location;

import lombok.Data;

@Data
public class ApiLocationDto {
    private Long id;
    private String name;
    private String type;
    private String dimension;
}
