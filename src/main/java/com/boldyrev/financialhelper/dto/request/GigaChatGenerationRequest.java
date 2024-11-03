package com.boldyrev.financialhelper.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * GigaChat request for categories generation.
 *
 * @author Alexandr Boldyrev
 */
@Data
@Builder
@Jacksonized
public class GigaChatGenerationRequest {

    private String model;

    @JsonProperty("messages")
    private List<ChatMessageDto> chatMessages;
}
