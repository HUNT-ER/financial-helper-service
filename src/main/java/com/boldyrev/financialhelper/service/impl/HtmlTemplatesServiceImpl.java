package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.dto.HtmlReceiptAttributes;
import com.boldyrev.financialhelper.service.HtmlTemplatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Implementation of {@link HtmlTemplatesService}.
 *
 * @author Alexandr Boldyrev
 */
@Service
@RequiredArgsConstructor
public class HtmlTemplatesServiceImpl implements HtmlTemplatesService {

    private final TemplateEngine templateEngine;

    @Override
    public Mono<String> formHtmlReceipt(HtmlReceiptAttributes receiptAttributes) {
        return Mono.fromCallable(() -> {
                    Context context = new Context();
                    context.setVariable("receiptAttributes", receiptAttributes);

                    return templateEngine.process("receipt", context);
                }
            )
            .subscribeOn(Schedulers.boundedElastic());
    }
}
