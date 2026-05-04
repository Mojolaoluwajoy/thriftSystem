package com.thrift.thriftsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

//    @Bean
//    public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean factoryBean) {
//        return new ValidatingMongoEventListener(factoryBean);
//    }
//    @Bean
//    public LocalValidatorFactoryBean localValidatorFactoryBean() {
//        return new LocalValidatorFactoryBean();
//    }
//
//@Bean
//    public MongoCustomConversions mongoCustomConversions() {
//        return new MongoCustomConversions(Collections.emptyList());
//}
}

