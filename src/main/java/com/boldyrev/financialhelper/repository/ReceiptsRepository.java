package com.boldyrev.financialhelper.repository;

import com.boldyrev.financialhelper.model.Receipt;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Receipt documents reactive repository.
 *
 * @author Alexandr Boldyrev
 */
public interface ReceiptsRepository extends ReactiveMongoRepository<Receipt, Long> {

    @Query("{ 'userId': ?0, 'receiptQrData.fn': ?1, 'receiptQrData.fd': ?2, 'receiptQrData.fp': ?3, 'receiptQrData.checkTime': ?4, 'receiptQrData.type' : ?5, 'receiptQrData.sum' : ?6 }")
    Mono<Receipt> findByUserQrData(Long userId, String fn, String fd, String fp, String checkTime, String type,
        String sum);
}
