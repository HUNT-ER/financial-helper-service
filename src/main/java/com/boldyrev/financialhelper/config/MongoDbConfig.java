package com.boldyrev.financialhelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * MongoDB configuration.
 *
 * @author Alexandr Boldyrev
 */
@Configuration
@EnableReactiveMongoAuditing
public class MongoDbConfig {

    @Bean
    public ReactiveMongoTransactionManager transactionManager(
        ReactiveMongoDatabaseFactory dbFactory) {
        return new ReactiveMongoTransactionManager(dbFactory);
    }

    @Bean
    public TransactionalOperator transactionalOperator(
        ReactiveTransactionManager transactionManager) {
        return TransactionalOperator.create(transactionManager);
    }
}
