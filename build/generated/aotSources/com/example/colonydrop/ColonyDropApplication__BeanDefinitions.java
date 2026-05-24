package com.example.colonydrop;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ColonyDropApplication}.
 */
@Generated
public class ColonyDropApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'colonyDropApplication'.
   */
  public static BeanDefinition getColonyDropApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ColonyDropApplication.class);
    beanDefinition.setInstanceSupplier(ColonyDropApplication::new);
    return beanDefinition;
  }
}
