package com.example.colonydrop.controller.chat;

import com.example.colonydrop.service.kafka.ChatProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Bean definitions for {@link ChatController}.
 */
@Generated
public class ChatController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'chatController'.
   */
  private static BeanInstanceSupplier<ChatController> getChatControllerInstanceSupplier() {
    return BeanInstanceSupplier.<ChatController>forConstructor(ChatProducer.class, StringRedisTemplate.class, ObjectMapper.class)
            .withGenerator((registeredBean, args) -> new ChatController(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'chatController'.
   */
  public static BeanDefinition getChatControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ChatController.class);
    beanDefinition.setInstanceSupplier(getChatControllerInstanceSupplier());
    return beanDefinition;
  }
}
