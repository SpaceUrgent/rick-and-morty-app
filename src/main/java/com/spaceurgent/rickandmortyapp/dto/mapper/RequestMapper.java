package com.spaceurgent.rickandmortyapp.dto.mapper;

public interface RequestMapper<T, Q> {
    T toModel(Q dto);
}
