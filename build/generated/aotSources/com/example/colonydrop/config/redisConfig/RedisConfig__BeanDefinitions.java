package com.example.colonydrop.config.redisConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Bean definitions for {@link RedisConfig}.
 */
@Generated
public class RedisConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'redisConfig'.
   */
  public static BeanDefinition getRedisConfigBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RedisConfig.class);
    beanDefinition.setTargetType(RedisConfig.class);
    ConfigurationClassUtils.initializeConfigurationClass(RedisConfig.class);
    beanDefinition.setInstanceSupplier(RedisConfig$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'objectMapper'.
   */
  private static BeanInstanceSupplier<ObjectMapper> getObjectMapperInstanceSupplier() {
    return BeanInstanceSupplier.<ObjectMapper>forFactoryMethod(RedisConfig$$SpringCGLIB$$0.class, "objectMapper")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("redisConfig", RedisConfig.class).objectMapper());
  }

  /**
   * Get the bean definition for 'objectMapper'.
   */
  public static BeanDefinition getObjectMapperBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ObjectMapper.class);
    beanDefinition.setFactoryBeanName("redisConfig");
    beanDefinition.setInstanceSupplier(getObjectMapperInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'redisMessageListenerContainer'.
   */
  private static BeanInstanceSupplier<RedisMessageListenerContainer> getRedisMessageListenerContainerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<RedisMessageListenerContainer>forFactoryMethod(RedisConfig$$SpringCGLIB$$0.class, "redisMessageListenerContainer", RedisConnectionFactory.class, RedisSubscriber.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("redisConfig", RedisConfig.class).redisMessageListenerContainer(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'redisMessageListenerContainer'.
   */
  public static BeanDefinition getRedisMessageListenerContainerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RedisMessageListenerContainer.class);
    beanDefinition.setFactoryBeanName("redisConfig");
    beanDefinition.setInstanceSupplier(getRedisMessageListenerContainerInstanceSupplier());
    return beanDefinition;
  }
}
