package com.boldyrev.financialhelper.dto.response;

import com.boldyrev.financialhelper.dto.request.ChatMessageDto;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GigaChat response from categories generation.
 *
 * @author Alexandr Boldyrev
 */
@Data
@NoArgsConstructor
public class GigaChatGenerationResponse {

    private List<GeneratedMessagesDto> choices;

    private long created;

    private String model;

    private String object;

    private GigaChatUsageDto gigaChatUsageDto;

    @Data
    @NoArgsConstructor
    public static class GeneratedMessagesDto {

        private ChatMessageDto message;

        private int index;

        private String finishReason;
    }

    @Data
    @NoArgsConstructor
    public static class GigaChatUsageDto {

        private int promptTokens;

        private int completionTokens;

        private int totalTokens;
    }
}
