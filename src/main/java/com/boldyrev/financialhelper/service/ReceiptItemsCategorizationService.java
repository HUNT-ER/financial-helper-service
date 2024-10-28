package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.model.ReceiptItem;
import com.boldyrev.financialhelper.model.TransactionCategory;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
public interface ReceiptItemsCategorizationService {

    Mono<Map<String, TransactionCategory>> categorizeItems(List<ReceiptItem> items);
}
