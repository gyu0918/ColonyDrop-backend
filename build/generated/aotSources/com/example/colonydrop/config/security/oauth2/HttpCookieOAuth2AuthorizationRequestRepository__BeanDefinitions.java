package com.example.colonydrop.config.security.oauth2;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HttpCookieOAuth2AuthorizationRequestRepository}.
 */
@Generated
public class HttpCookieOAuth2AuthorizationRequestRepository__BeanDefinitions {
  /**
   * Get the bean definition for 'httpCookieOAuth2AuthorizationRequestRepository'.
   */
  public static BeanDefinition getHttpCookieOAuthAuthorizationRequestRepositoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HttpCookieOAuth2AuthorizationRequestRepository.class);
    beanDefinition.setInstanceSupplier(HttpCookieOAuth2AuthorizationRequestRepository::new);
    return beanDefinition;
  }
}
