package com.boldyrev.financialhelper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
@Data
@NoArgsConstructor
public class AuthTokenDto {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_at")
    private Long expiresAt;
}
