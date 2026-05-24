package com.example.colonydrop.config.security.handler;

import com.example.colonydrop.config.security.oauth2.JwtProperties;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Bean definitions for {@link OAuth2SuccessHandler}.
 */
@Generated
public class OAuth2SuccessHandler__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'OAuth2SuccessHandler'.
   */
  private static BeanInstanceSupplier<OAuth2SuccessHandler> getOAuthSuccessHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<OAuth2SuccessHandler>forConstructor(JwtProperties.class, StringRedisTemplate.class)
            .withGenerator((registeredBean, args) -> new OAuth2SuccessHandler(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'oAuth2SuccessHandler'.
   */
  public static BeanDefinition getOAuthSuccessHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2SuccessHandler.class);
    beanDefinition.setInstanceSupplier(getOAuthSuccessHandlerInstanceSupplier());
    return beanDefinition;
  }
}
