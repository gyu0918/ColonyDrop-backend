package com.example.colonydrop.service.payment;

import com.example.colonydrop.repository.order.OrderRepository;
import com.siot.IamportRestClient.IamportClient;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PaymentService}.
 */
@Generated
public class PaymentService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'paymentService'.
   */
  private static BeanInstanceSupplier<PaymentService> getPaymentServiceInstanceSupplier() {
    return BeanInstanceSupplier.<PaymentService>forConstructor(OrderRepository.class, IamportClient.class)
            .withGenerator((registeredBean, args) -> new PaymentService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'paymentService'.
   */
  public static BeanDefinition getPaymentServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PaymentService.class);
    beanDefinition.setInstanceSupplier(getPaymentServiceInstanceSupplier());
    return beanDefinition;
  }
}
