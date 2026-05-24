package org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OAuth2AuthorizationServerAutoConfiguration}.
 */
@Generated
public class OAuth2AuthorizationServerAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'oAuth2AuthorizationServerAutoConfiguration'.
   */
  public static BeanDefinition getOAuthAuthorizationServerAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2AuthorizationServerAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(OAuth2AuthorizationServerAutoConfiguration::new);
    return beanDefinition;
  }
}
