package com.boldyrev.financialhelper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

/**
 * MongoDB auditing configuration.
 *
 * @author Alexandr Boldyrev
 */
@Configuration
@EnableReactiveMongoAuditing
public class MongoDbAuditingConfig {

}
