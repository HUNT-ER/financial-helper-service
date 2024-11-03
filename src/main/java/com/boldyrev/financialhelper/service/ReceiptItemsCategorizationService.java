package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.dto.TransactionCategoryDto;
import com.boldyrev.financialhelper.model.ReceiptItem;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;

/**
 * Service for process categorization receipt items.
 *
 * @author Alexandr Boldyrev
 */
public interface ReceiptItemsCategorizationService {

    /**
     * Categorize {@link ReceiptItem} by {@link TransactionCategoryDto}.
     *
     * @param items list of {@link ReceiptItem}.
     * @return categorized items in map with key equal {@link ReceiptItem#getName()} and value as
     * mapped {@link TransactionCategoryDto}.
     */
    Mono<Map<String, TransactionCategoryDto>> categorizeItems(List<ReceiptItem> items);
}
