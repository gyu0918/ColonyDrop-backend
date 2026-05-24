package org.springframework.boot.security.oauth2.server.resource.autoconfigure.servlet;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link Oauth2ResourceServerConfiguration}.
 */
@Generated
public class Oauth2ResourceServerConfiguration__BeanDefinitions {
  /**
   * Bean definitions for {@link Oauth2ResourceServerConfiguration.JwtConfiguration}.
   */
  @Generated
  public static class JwtConfiguration {
    /**
     * Get the bean definition for 'jwtConfiguration'.
     */
    public static BeanDefinition getJwtConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(Oauth2ResourceServerConfiguration.JwtConfiguration.class);
      beanDefinition.setInstanceSupplier(Oauth2ResourceServerConfiguration.JwtConfiguration::new);
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link Oauth2ResourceServerConfiguration.OpaqueTokenConfiguration}.
   */
  @Generated
  public static class OpaqueTokenConfiguration {
    /**
     * Get the bean definition for 'opaqueTokenConfiguration'.
     */
    public static BeanDefinition getOpaqueTokenConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(Oauth2ResourceServerConfiguration.OpaqueTokenConfiguration.class);
      beanDefinition.setInstanceSupplier(Oauth2ResourceServerConfiguration.OpaqueTokenConfiguration::new);
      return beanDefinition;
    }
  }
}
