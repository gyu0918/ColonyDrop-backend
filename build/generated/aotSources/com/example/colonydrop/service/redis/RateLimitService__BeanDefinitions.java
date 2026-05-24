package com.example.colonydrop.service.redis;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Bean definitions for {@link RateLimitService}.
 */
@Generated
public class RateLimitService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'rateLimitService'.
   */
  private static BeanInstanceSupplier<RateLimitService> getRateLimitServiceInstanceSupplier() {
    return BeanInstanceSupplier.<RateLimitService>forConstructor(StringRedisTemplate.class)
            .withGenerator((registeredBean, args) -> new RateLimitService(args.get(0)));
  }

  /**
   * Get the bean definition for 'rateLimitService'.
   */
  public static BeanDefinition getRateLimitServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RateLimitService.class);
    beanDefinition.setInstanceSupplier(getRateLimitServiceInstanceSupplier());
    return beanDefinition;
  }
}
