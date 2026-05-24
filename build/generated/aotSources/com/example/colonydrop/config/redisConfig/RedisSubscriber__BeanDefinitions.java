package com.example.colonydrop.config.redisConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Bean definitions for {@link RedisSubscriber}.
 */
@Generated
public class RedisSubscriber__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'redisSubscriber'.
   */
  private static BeanInstanceSupplier<RedisSubscriber> getRedisSubscriberInstanceSupplier() {
    return BeanInstanceSupplier.<RedisSubscriber>forConstructor(SimpMessagingTemplate.class, ObjectMapper.class)
            .withGenerator((registeredBean, args) -> new RedisSubscriber(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'redisSubscriber'.
   */
  public static BeanDefinition getRedisSubscriberBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RedisSubscriber.class);
    beanDefinition.setInstanceSupplier(getRedisSubscriberInstanceSupplier());
    return beanDefinition;
  }
}
