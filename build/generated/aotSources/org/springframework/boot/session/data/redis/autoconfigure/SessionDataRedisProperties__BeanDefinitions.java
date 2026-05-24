package org.springframework.boot.session.data.redis.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SessionDataRedisProperties}.
 */
@Generated
public class SessionDataRedisProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'sessionDataRedisProperties'.
   */
  public static BeanDefinition getSessionDataRedisPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionDataRedisProperties.class);
    beanDefinition.setInstanceSupplier(SessionDataRedisProperties::new);
    return beanDefinition;
  }
}
