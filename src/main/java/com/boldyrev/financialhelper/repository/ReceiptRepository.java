package com.boldyrev.financialhelper.repository;

import com.boldyrev.financialhelper.model.Receipt;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Receipt documents reactive repository.
 *
 * @author Alexandr Boldyrev
 */
public interface ReceiptRepository extends ReactiveMongoRepository<Receipt, Long> {

}
