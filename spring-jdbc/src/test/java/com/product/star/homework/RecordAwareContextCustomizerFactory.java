package com.product.star.homework;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;

import java.util.List;

// Auto-discovered via META-INF/spring.factories.
// Registers a record-aware AutowiredAnnotationBeanPostProcessor for all Spring tests
// in this module to prevent IllegalAccessException on final record fields in Java 17.
public class RecordAwareContextCustomizerFactory implements ContextCustomizerFactory {

    @Override
    public ContextCustomizer createContextCustomizer(Class<?> testClass,
                                                     List<org.springframework.test.context.ContextConfigurationAttributes> configAttributes) {
        return new RecordAwareContextCustomizer();
    }

    private static class RecordAwareContextCustomizer implements ContextCustomizer {

        @Override
        public void customizeContext(org.springframework.context.ConfigurableApplicationContext context,
                                     MergedContextConfiguration mergedConfig) {
            if (context instanceof BeanDefinitionRegistry registry) {
                var definition = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class) {
                    {
                        setBeanClass(RecordSkippingProcessor.class);
                    }
                };
                registry.registerBeanDefinition(
                        "org.springframework.context.annotation.internalAutowiredAnnotationProcessor",
                        definition
                );
            }
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof RecordAwareContextCustomizer;
        }

        @Override
        public int hashCode() {
            return RecordAwareContextCustomizer.class.hashCode();
        }
    }

    public static class RecordSkippingProcessor extends AutowiredAnnotationBeanPostProcessor {
        @Override
        public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
            if (bean.getClass().isRecord()) {
                return pvs;
            }
            return super.postProcessProperties(pvs, bean, beanName);
        }
    }
}
