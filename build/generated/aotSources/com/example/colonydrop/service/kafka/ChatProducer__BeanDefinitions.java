package com.example.colonydrop.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Bean definitions for {@link ChatProducer}.
 */
@Generated
public class ChatProducer__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'chatProducer'.
   */
  private static BeanInstanceSupplier<ChatProducer> getChatProducerInstanceSupplier() {
    return BeanInstanceSupplier.<ChatProducer>forConstructor(KafkaTemplate.class, ObjectMapper.class)
            .withGenerator((registeredBean, args) -> new ChatProducer(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'chatProducer'.
   */
  public static BeanDefinition getChatProducerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ChatProducer.class);
    beanDefinition.setInstanceSupplier(getChatProducerInstanceSupplier());
    return beanDefinition;
  }
}
