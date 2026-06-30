package com.product.star.homework;

import org.hibernate.SessionFactory;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        var configuration = new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Contact.class)
                .configure();
        return configuration.buildSessionFactory();
    }

    @Bean
    public ContactDao contactDao() {
        return new ContactDao(sessionFactory());
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
