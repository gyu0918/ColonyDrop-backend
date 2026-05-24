package com.example.colonydrop.config.redisConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Bean definitions for {@link RedisPublisher}.
 */
@Generated
public class RedisPublisher__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'redisPublisher'.
   */
  private static BeanInstanceSupplier<RedisPublisher> getRedisPublisherInstanceSupplier() {
    return BeanInstanceSupplier.<RedisPublisher>forConstructor(StringRedisTemplate.class, ObjectMapper.class)
            .withGenerator((registeredBean, args) -> new RedisPublisher(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'redisPublisher'.
   */
  public static BeanDefinition getRedisPublisherBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RedisPublisher.class);
    beanDefinition.setInstanceSupplier(getRedisPublisherInstanceSupplier());
    return beanDefinition;
  }
}
