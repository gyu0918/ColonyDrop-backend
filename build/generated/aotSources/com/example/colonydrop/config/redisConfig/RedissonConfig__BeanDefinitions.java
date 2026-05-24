package com.example.colonydrop.config.redisConfig;

import org.redisson.api.RedissonClient;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link RedissonConfig}.
 */
@Generated
public class RedissonConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'redissonConfig'.
   */
  public static BeanDefinition getRedissonConfigBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RedissonConfig.class);
    beanDefinition.setTargetType(RedissonConfig.class);
    ConfigurationClassUtils.initializeConfigurationClass(RedissonConfig.class);
    InstanceSupplier<RedissonConfig> instanceSupplier = InstanceSupplier.using(RedissonConfig$$SpringCGLIB$$0::new);
    instanceSupplier = instanceSupplier.andThen(RedissonConfig__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'redissonClient'.
   */
  private static BeanInstanceSupplier<RedissonClient> getRedissonClientInstanceSupplier() {
    return BeanInstanceSupplier.<RedissonClient>forFactoryMethod(RedissonConfig$$SpringCGLIB$$0.class, "redissonClient")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("redissonConfig", RedissonConfig.class).redissonClient());
  }

  /**
   * Get the bean definition for 'redissonClient'.
   */
  public static BeanDefinition getRedissonClientBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RedissonClient.class);
    beanDefinition.setDestroyMethodNames("shutdown");
    beanDefinition.setFactoryBeanName("redissonConfig");
    beanDefinition.setInstanceSupplier(getRedissonClientInstanceSupplier());
    return beanDefinition;
  }
}
