package org.springframework.boot.security.oauth2.server.resource.autoconfigure.servlet;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OAuth2ResourceServerOpaqueTokenConfiguration}.
 */
@Generated
public class OAuth2ResourceServerOpaqueTokenConfiguration__BeanDefinitions {
  /**
   * Bean definitions for {@link OAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration}.
   */
  @Generated
  public static class OpaqueTokenIntrospectionClientConfiguration {
    /**
     * Get the bean definition for 'opaqueTokenIntrospectionClientConfiguration'.
     */
    public static BeanDefinition getOpaqueTokenIntrospectionClientConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration.class);
      beanDefinition.setInstanceSupplier(OAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration::new);
      return beanDefinition;
    }
  }
}
