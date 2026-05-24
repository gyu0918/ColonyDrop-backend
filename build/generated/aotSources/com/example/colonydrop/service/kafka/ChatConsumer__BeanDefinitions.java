package com.example.colonydrop.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Bean definitions for {@link ChatConsumer}.
 */
@Generated
public class ChatConsumer__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'chatConsumer'.
   */
  private static BeanInstanceSupplier<ChatConsumer> getChatConsumerInstanceSupplier() {
    return BeanInstanceSupplier.<ChatConsumer>forConstructor(ObjectMapper.class, SimpMessagingTemplate.class, StringRedisTemplate.class)
            .withGenerator((registeredBean, args) -> new ChatConsumer(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'chatConsumer'.
   */
  public static BeanDefinition getChatConsumerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ChatConsumer.class);
    beanDefinition.setInstanceSupplier(getChatConsumerInstanceSupplier());
    return beanDefinition;
  }
}
