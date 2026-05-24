package com.example.colonydrop.controller.payment;

import com.example.colonydrop.service.payment.PaymentService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PaymentController}.
 */
@Generated
public class PaymentController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'paymentController'.
   */
  private static BeanInstanceSupplier<PaymentController> getPaymentControllerInstanceSupplier() {
    return BeanInstanceSupplier.<PaymentController>forConstructor(PaymentService.class)
            .withGenerator((registeredBean, args) -> new PaymentController(args.get(0)));
  }

  /**
   * Get the bean definition for 'paymentController'.
   */
  public static BeanDefinition getPaymentControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PaymentController.class);
    beanDefinition.setInstanceSupplier(getPaymentControllerInstanceSupplier());
    return beanDefinition;
  }
}
