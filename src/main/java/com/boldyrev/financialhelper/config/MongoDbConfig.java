package com.boldyrev.financialhelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
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
    public ReactiveMongoTransactionManager mongoTransactionManager(
        ReactiveMongoDatabaseFactory dbFactory) {
        return new ReactiveMongoTransactionManager(dbFactory);
    }

    @Bean
    public TransactionalOperator mongoTransactionalOperator(
        ReactiveMongoTransactionManager transactionManager) {
        return TransactionalOperator.create(transactionManager);
    }
}
