package com.example.colonydrop.service.order;

import com.example.colonydrop.repository.item.ItemRepository;
import com.example.colonydrop.repository.order.OrderRepository;
import org.redisson.api.RedissonClient;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OrderService}.
 */
@Generated
public class OrderService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'orderService'.
   */
  private static BeanInstanceSupplier<OrderService> getOrderServiceInstanceSupplier() {
    return BeanInstanceSupplier.<OrderService>forConstructor(OrderRepository.class, ItemRepository.class, RedissonClient.class)
            .withGenerator((registeredBean, args) -> new OrderService(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'orderService'.
   */
  public static BeanDefinition getOrderServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrderService.class);
    beanDefinition.setInstanceSupplier(getOrderServiceInstanceSupplier());
    return beanDefinition;
  }
}
