package com.boldyrev.financialhelper.model;

import java.time.Instant;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representation of receipt document.
 *
 * @author Alexandr Boldyrev
 */
@Data
@Document(collection = "receipts")
public class Receipt {

    @Id
    private ObjectId id;

    private Long userId;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;

    private ReceiptData receiptData;

    private ReceiptQrData receiptQrData;
}