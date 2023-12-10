package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResponseDTO {

    //this variable name must be exactly like this. Because keycloak send the token data with this name and the names have to match.
    private String access_token;

}
