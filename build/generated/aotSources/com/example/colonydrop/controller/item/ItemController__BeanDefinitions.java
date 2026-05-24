package com.example.colonydrop.controller.item;

import com.example.colonydrop.repository.item.ItemRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ItemController}.
 */
@Generated
public class ItemController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'itemController'.
   */
  private static BeanInstanceSupplier<ItemController> getItemControllerInstanceSupplier() {
    return BeanInstanceSupplier.<ItemController>forConstructor(ItemRepository.class)
            .withGenerator((registeredBean, args) -> new ItemController(args.get(0)));
  }

  /**
   * Get the bean definition for 'itemController'.
   */
  public static BeanDefinition getItemControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ItemController.class);
    beanDefinition.setInstanceSupplier(getItemControllerInstanceSupplier());
    return beanDefinition;
  }
}
