package com.spaceurgent.rickandmortyapp.dto.mapper;

public interface ResponseMapper<R, T> {
    R toDto(T model);
}
