package com.example.colonydrop.service.kafka;

import com.example.colonydrop.config.redisConfig.RedisPublisher;
import com.example.colonydrop.repository.member.MemberRepository;
import com.example.colonydrop.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OrderConsumer}.
 */
@Generated
public class OrderConsumer__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'orderConsumer'.
   */
  private static BeanInstanceSupplier<OrderConsumer> getOrderConsumerInstanceSupplier() {
    return BeanInstanceSupplier.<OrderConsumer>forConstructor(OrderService.class, MemberRepository.class, RedisPublisher.class, ObjectMapper.class)
            .withGenerator((registeredBean, args) -> new OrderConsumer(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'orderConsumer'.
   */
  public static BeanDefinition getOrderConsumerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrderConsumer.class);
    beanDefinition.setInstanceSupplier(getOrderConsumerInstanceSupplier());
    return beanDefinition;
  }
}
