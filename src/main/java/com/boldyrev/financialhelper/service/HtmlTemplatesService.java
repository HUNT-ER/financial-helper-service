package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.dto.HtmlReceiptAttributes;
import reactor.core.publisher.Mono;

/**
 * Service for making html pages by template.
 *
 * @author Alexandr Boldyrev
 */
public interface HtmlTemplatesService {

    /**
     * Form HTML receipt.
     *
     * @param receiptAttributes receipt attributes.
     * @return HTML receipt.
     */
    Mono<String> formHtmlReceipt(HtmlReceiptAttributes receiptAttributes);
}
