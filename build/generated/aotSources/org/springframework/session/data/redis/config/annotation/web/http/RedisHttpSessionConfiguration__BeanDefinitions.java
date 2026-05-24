package org.springframework.session.data.redis.config.annotation.web.http;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.session.data.redis.RedisSessionRepository;

/**
 * Bean definitions for {@link RedisHttpSessionConfiguration}.
 */
@Generated
public class RedisHttpSessionConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'redisHttpSessionConfiguration'.
   */
  public static BeanDefinition getRedisHttpSessionConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RedisHttpSessionConfiguration.class);
    InstanceSupplier<RedisHttpSessionConfiguration> instanceSupplier = InstanceSupplier.using(RedisHttpSessionConfiguration::new);
    instanceSupplier = instanceSupplier.andThen(RedisHttpSessionConfiguration__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'sessionRepository'.
   */
  private static BeanInstanceSupplier<RedisSessionRepository> getSessionRepositoryInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<RedisSessionRepository>forFactoryMethod(RedisHttpSessionConfiguration.class, "sessionRepository")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration", RedisHttpSessionConfiguration.class).sessionRepository());
  }

  /**
   * Get the bean definition for 'sessionRepository'.
   */
  public static BeanDefinition getSessionRepositoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RedisSessionRepository.class);
    beanDefinition.setFactoryBeanName("org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration");
    beanDefinition.setInstanceSupplier(getSessionRepositoryInstanceSupplier());
    return beanDefinition;
  }
}
