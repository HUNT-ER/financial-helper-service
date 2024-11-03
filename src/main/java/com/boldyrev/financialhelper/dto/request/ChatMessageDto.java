package com.boldyrev.financialhelper.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * GigaChat dto message from generation API.
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
