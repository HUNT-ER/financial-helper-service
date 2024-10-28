package com.boldyrev.financialhelper.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
@Data
@Builder
@Jacksonized
public class ChatMessageDto {

    private String role;

    private String content;
}
