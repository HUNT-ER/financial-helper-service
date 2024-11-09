package com.boldyrev.financialhelper.service.listener;

import com.boldyrev.financialhelper.dto.QrCodeReceiptMessageDto;
import com.boldyrev.financialhelper.service.ReceiptsService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Listener for QR-code receipt messages from RabbitMQ.
 *
 * @author Alexandr Boldyrev
 */
@Service
@RequiredArgsConstructor
public class RabbitMqQrReceiptListener {

    private final ReceiptsService receiptsService;

    @RabbitListener(queues = "${app.rabbitmq.queue.qr-receipt}")
    public void getQrCodeReceiptMessage(QrCodeReceiptMessageDto message) {
        receiptsService.processQrCodeReceipt(message)
            .subscribe();
    }
}
