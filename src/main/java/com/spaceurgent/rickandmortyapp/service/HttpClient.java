package com.spaceurgent.rickandmortyapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceurgent.rickandmortyapp.dto.external.ApiResponseDto;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpClient {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T extends ApiResponseDto> List<T> getResultsFromApi(String url,
                                                                   Class<T> responseClazz) {
        String currentUrl = url;
        List<T> responseDtos = new ArrayList<>();
        do {
            T apiResponseDto = sendGet(currentUrl, responseClazz);
            responseDtos.add(apiResponseDto);
            currentUrl = apiResponseDto.getInfo().getNext();
        } while (currentUrl != null);
        return responseDtos;
    }

    public <T> T sendGet(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
             return objectMapper.readValue(response.getEntity().getContent(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Can't fetch data from: " + url, e);
        }
    }
}
