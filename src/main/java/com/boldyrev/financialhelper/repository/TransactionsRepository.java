package com.boldyrev.financialhelper.repository;

import com.boldyrev.financialhelper.model.Transaction;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repository for {@link Transaction}.
 *
 * @author Alexandr Boldyrev
 */
public interface TransactionsRepository extends ReactiveCrudRepository<Transaction, UUID> {

}
