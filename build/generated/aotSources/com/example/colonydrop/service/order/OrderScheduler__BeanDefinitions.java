package com.example.colonydrop.service.order;

import com.example.colonydrop.repository.order.OrderRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OrderScheduler}.
 */
@Generated
public class OrderScheduler__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'orderScheduler'.
   */
  private static BeanInstanceSupplier<OrderScheduler> getOrderSchedulerInstanceSupplier() {
    return BeanInstanceSupplier.<OrderScheduler>forConstructor(OrderRepository.class)
            .withGenerator((registeredBean, args) -> new OrderScheduler(args.get(0)));
  }

  /**
   * Get the bean definition for 'orderScheduler'.
   */
  public static BeanDefinition getOrderSchedulerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrderScheduler.class);
    beanDefinition.setInstanceSupplier(getOrderSchedulerInstanceSupplier());
    return beanDefinition;
  }
}
