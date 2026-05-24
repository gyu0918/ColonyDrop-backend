package org.springframework.boot.session.data.redis.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.session.autoconfigure.SessionTimeout;
import org.springframework.core.ResolvableType;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.data.redis.RedisSessionRepository;

/**
 * Bean definitions for {@link SessionDataRedisAutoConfiguration}.
 */
@Generated
public class SessionDataRedisAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'sessionDataRedisAutoConfiguration'.
   */
  public static BeanDefinition getSessionDataRedisAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionDataRedisAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(SessionDataRedisAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link SessionDataRedisAutoConfiguration.ServletRedisSessionConfiguration}.
   */
  @Generated
  public static class ServletRedisSessionConfiguration {
    /**
     * Get the bean definition for 'servletRedisSessionConfiguration'.
     */
    public static BeanDefinition getServletRedisSessionConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionDataRedisAutoConfiguration.ServletRedisSessionConfiguration.class);
      beanDefinition.setInstanceSupplier(SessionDataRedisAutoConfiguration.ServletRedisSessionConfiguration::new);
      return beanDefinition;
    }

    /**
     * Bean definitions for {@link SessionDataRedisAutoConfiguration.ServletRedisSessionConfiguration.DefaultRedisSessionConfiguration}.
     */
    @Generated
    public static class DefaultRedisSessionConfiguration {
      /**
       * Get the bean definition for 'defaultRedisSessionConfiguration'.
       */
      public static BeanDefinition getDefaultRedisSessionConfigurationBeanDefinition() {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionDataRedisAutoConfiguration.ServletRedisSessionConfiguration.DefaultRedisSessionConfiguration.class);
        beanDefinition.setInstanceSupplier(SessionDataRedisAutoConfiguration.ServletRedisSessionConfiguration.DefaultRedisSessionConfiguration::new);
        return beanDefinition;
      }

      /**
       * Get the bean instance supplier for 'springBootSessionRepositoryCustomizer'.
       */
      private static BeanInstanceSupplier<SessionRepositoryCustomizer> getSpringBootSessionRepositoryCustomizerInstanceSupplier(
          ) {
        return BeanInstanceSupplier.<SessionRepositoryCustomizer>forFactoryMethod(SessionDataRedisAutoConfiguration.ServletRedisSessionConfiguration.DefaultRedisSessionConfiguration.class, "springBootSessionRepositoryCustomizer", SessionDataRedisProperties.class, SessionTimeout.class)
                .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.session.data.redis.autoconfigure.SessionDataRedisAutoConfiguration$ServletRedisSessionConfiguration$DefaultRedisSessionConfiguration", SessionDataRedisAutoConfiguration.ServletRedisSessionConfiguration.DefaultRedisSessionConfiguration.class).springBootSessionRepositoryCustomizer(args.get(0), args.get(1)));
      }

      /**
       * Get the bean definition for 'springBootSessionRepositoryCustomizer'.
       */
      public static BeanDefinition getSpringBootSessionRepositoryCustomizerBeanDefinition() {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionRepositoryCustomizer.class);
        beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(SessionRepositoryCustomizer.class, RedisSessionRepository.class));
        beanDefinition.setFactoryBeanName("org.springframework.boot.session.data.redis.autoconfigure.SessionDataRedisAutoConfiguration$ServletRedisSessionConfiguration$DefaultRedisSessionConfiguration");
        beanDefinition.setInstanceSupplier(getSpringBootSessionRepositoryCustomizerInstanceSupplier());
        return beanDefinition;
      }
    }
  }
}
