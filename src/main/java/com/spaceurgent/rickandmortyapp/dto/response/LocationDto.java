package com.spaceurgent.rickandmortyapp.dto.response;

import lombok.Data;

@Data
public class LocationDto {
    private Long id;
    private String name;
    private String type;
    private String dimension;
    private Long residents;
    private Long origins;
}
