package com.example.colonydrop.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Bean definitions for {@link OrderProducer}.
 */
@Generated
public class OrderProducer__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'orderProducer'.
   */
  private static BeanInstanceSupplier<OrderProducer> getOrderProducerInstanceSupplier() {
    return BeanInstanceSupplier.<OrderProducer>forConstructor(KafkaTemplate.class, ObjectMapper.class)
            .withGenerator((registeredBean, args) -> new OrderProducer(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'orderProducer'.
   */
  public static BeanDefinition getOrderProducerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrderProducer.class);
    beanDefinition.setInstanceSupplier(getOrderProducerInstanceSupplier());
    return beanDefinition;
  }
}
