package com.spaceurgent.rickandmortyapp.dto.response;

import lombok.Data;
import org.apache.commons.codec.binary.Base64;

@Data
public class MovieCharacterDto {
    private Long id;
    private String name;
    private String status;
    private String spieces;
    private String type;
    private String gender;
    private String origin;
    private String resident;
    private byte[] image;

    public String encodeImage() {
        return Base64.encodeBase64String(image);
    }
}
