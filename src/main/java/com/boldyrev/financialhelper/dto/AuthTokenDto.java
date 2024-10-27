package com.boldyrev.financialhelper.dto;

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

    private String accessToken;

    private Long expiresAt;
}
