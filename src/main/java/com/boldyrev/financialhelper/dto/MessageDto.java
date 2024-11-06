package com.boldyrev.financialhelper.dto;

import com.boldyrev.financialhelper.enums.MessageType;
import com.boldyrev.financialhelper.enums.UserMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto to RabbitMQ message.
 *
 * @author Alexandr Boldyrev
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MessageDto {

    Long userId;

    UserMessage message;

    MessageType messageType;

    Object body;
}
