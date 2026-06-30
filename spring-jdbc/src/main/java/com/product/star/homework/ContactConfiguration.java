package com.product.star.homework;

import com.product.star.common.JdbcConfig;
import com.product.star.common.PropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@Import({JdbcConfig.class, PropertiesConfiguration.class})
public class ContactConfiguration {

    @Bean
    public ContactDao contactDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new ContactDao(namedParameterJdbcTemplate);
    }
}
