package com.example.colonydrop.config.redisConfig;

import com.example.colonydrop.config.security.oauth2.JwtProperties;
import com.example.colonydrop.service.redis.RateLimitService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link RateLimitInterceptor}.
 */
@Generated
public class RateLimitInterceptor__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'rateLimitInterceptor'.
   */
  private static BeanInstanceSupplier<RateLimitInterceptor> getRateLimitInterceptorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<RateLimitInterceptor>forConstructor(RateLimitService.class, JwtProperties.class)
            .withGenerator((registeredBean, args) -> new RateLimitInterceptor(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'rateLimitInterceptor'.
   */
  public static BeanDefinition getRateLimitInterceptorBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RateLimitInterceptor.class);
    beanDefinition.setInstanceSupplier(getRateLimitInterceptorInstanceSupplier());
    return beanDefinition;
  }
}
