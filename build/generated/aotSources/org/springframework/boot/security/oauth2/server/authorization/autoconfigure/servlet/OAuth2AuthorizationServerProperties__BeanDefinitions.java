package org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OAuth2AuthorizationServerProperties}.
 */
@Generated
public class OAuth2AuthorizationServerProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'oAuth2AuthorizationServerProperties'.
   */
  public static BeanDefinition getOAuthAuthorizationServerPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2AuthorizationServerProperties.class);
    beanDefinition.setInstanceSupplier(OAuth2AuthorizationServerProperties::new);
    return beanDefinition;
  }
}
