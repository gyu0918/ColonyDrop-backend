package com.example.colonydrop.config.security.oauth2;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link JwtProperties}.
 */
@Generated
public class JwtProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'jwtProperties'.
   */
  public static BeanDefinition getJwtPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(JwtProperties.class);
    beanDefinition.setInstanceSupplier(JwtProperties::new);
    return beanDefinition;
  }
}
