package com.product.star.homework;

import com.product.star.common.JdbcConfig;
import com.product.star.common.PropertiesConfiguration;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@Import({JdbcConfig.class, PropertiesConfiguration.class})
public class ContactConfiguration {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ContactConfiguration(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Bean
    public ContactDao contactDao() {
        return new ContactDao(namedParameterJdbcTemplate);
    }

    // Replaces the default AutowiredAnnotationBeanPostProcessor to skip field injection for Java records.
    // Records have final fields that cannot be set via reflection in Java 17+.
    // Constructor injection (via spring.test.constructor.autowire.mode=ALL) is used instead.
    @Bean(name = "org.springframework.context.annotation.internalAutowiredAnnotationProcessor")
    public static AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
        return new AutowiredAnnotationBeanPostProcessor() {
            @Override
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
                if (bean.getClass().isRecord()) {
                    return pvs;
                }
                return super.postProcessProperties(pvs, bean, beanName);
            }
        };
    }
}
