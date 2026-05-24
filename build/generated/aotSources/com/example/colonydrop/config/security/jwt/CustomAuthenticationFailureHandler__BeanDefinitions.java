package com.example.colonydrop.config.security.jwt;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CustomAuthenticationFailureHandler}.
 */
@Generated
public class CustomAuthenticationFailureHandler__BeanDefinitions {
  /**
   * Get the bean definition for 'customAuthenticationFailureHandler'.
   */
  public static BeanDefinition getCustomAuthenticationFailureHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomAuthenticationFailureHandler.class);
    beanDefinition.setInstanceSupplier(CustomAuthenticationFailureHandler::new);
    return beanDefinition;
  }
}
