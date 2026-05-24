package org.springframework.boot.session.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.web.server.autoconfigure.ServerProperties;

/**
 * Bean definitions for {@link SessionAutoConfiguration}.
 */
@Generated
public class SessionAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'sessionAutoConfiguration'.
   */
  public static BeanDefinition getSessionAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(SessionAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link SessionAutoConfiguration.ServletSessionConfiguration}.
   */
  @Generated
  public static class ServletSessionConfiguration {
    /**
     * Get the bean definition for 'servletSessionConfiguration'.
     */
    public static BeanDefinition getServletSessionConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionAutoConfiguration.ServletSessionConfiguration.class);
      beanDefinition.setInstanceSupplier(SessionAutoConfiguration.ServletSessionConfiguration::new);
      return beanDefinition;
    }

    /**
     * Bean definitions for {@link SessionAutoConfiguration.ServletSessionConfiguration.RememberMeServicesConfiguration}.
     */
    @Generated
    public static class RememberMeServicesConfiguration {
      /**
       * Get the bean definition for 'rememberMeServicesConfiguration'.
       */
      public static BeanDefinition getRememberMeServicesConfigurationBeanDefinition() {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionAutoConfiguration.ServletSessionConfiguration.RememberMeServicesConfiguration.class);
        beanDefinition.setInstanceSupplier(SessionAutoConfiguration.ServletSessionConfiguration.RememberMeServicesConfiguration::new);
        return beanDefinition;
      }

      /**
       * Get the bean instance supplier for 'rememberMeServicesCookieSerializerCustomizer'.
       */
      private static BeanInstanceSupplier<DefaultCookieSerializerCustomizer> getRememberMeServicesCookieSerializerCustomizerInstanceSupplier(
          ) {
        return BeanInstanceSupplier.<DefaultCookieSerializerCustomizer>forFactoryMethod(SessionAutoConfiguration.ServletSessionConfiguration.RememberMeServicesConfiguration.class, "rememberMeServicesCookieSerializerCustomizer")
                .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.session.autoconfigure.SessionAutoConfiguration$ServletSessionConfiguration$RememberMeServicesConfiguration", SessionAutoConfiguration.ServletSessionConfiguration.RememberMeServicesConfiguration.class).rememberMeServicesCookieSerializerCustomizer());
      }

      /**
       * Get the bean definition for 'rememberMeServicesCookieSerializerCustomizer'.
       */
      public static BeanDefinition getRememberMeServicesCookieSerializerCustomizerBeanDefinition() {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(DefaultCookieSerializerCustomizer.class);
        beanDefinition.setFactoryBeanName("org.springframework.boot.session.autoconfigure.SessionAutoConfiguration$ServletSessionConfiguration$RememberMeServicesConfiguration");
        beanDefinition.setInstanceSupplier(getRememberMeServicesCookieSerializerCustomizerInstanceSupplier());
        return beanDefinition;
      }
    }

    /**
     * Bean definitions for {@link SessionAutoConfiguration.ServletSessionConfiguration.EmbeddedWebServerConfiguration}.
     */
    @Generated
    public static class EmbeddedWebServerConfiguration {
      /**
       * Get the bean definition for 'embeddedWebServerConfiguration'.
       */
      public static BeanDefinition getEmbeddedWebServerConfigurationBeanDefinition() {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionAutoConfiguration.ServletSessionConfiguration.EmbeddedWebServerConfiguration.class);
        beanDefinition.setInstanceSupplier(SessionAutoConfiguration.ServletSessionConfiguration.EmbeddedWebServerConfiguration::new);
        return beanDefinition;
      }

      /**
       * Get the bean instance supplier for 'embeddedWebServerSessionTimeout'.
       */
      private static BeanInstanceSupplier<SessionTimeout> getEmbeddedWebServerSessionTimeoutInstanceSupplier(
          ) {
        return BeanInstanceSupplier.<SessionTimeout>forFactoryMethod(SessionAutoConfiguration.ServletSessionConfiguration.EmbeddedWebServerConfiguration.class, "embeddedWebServerSessionTimeout", SessionProperties.class, ServerProperties.class)
                .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.session.autoconfigure.SessionAutoConfiguration$ServletSessionConfiguration$EmbeddedWebServerConfiguration", SessionAutoConfiguration.ServletSessionConfiguration.EmbeddedWebServerConfiguration.class).embeddedWebServerSessionTimeout(args.get(0), args.get(1)));
      }

      /**
       * Get the bean definition for 'embeddedWebServerSessionTimeout'.
       */
      public static BeanDefinition getEmbeddedWebServerSessionTimeoutBeanDefinition() {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionTimeout.class);
        beanDefinition.setFactoryBeanName("org.springframework.boot.session.autoconfigure.SessionAutoConfiguration$ServletSessionConfiguration$EmbeddedWebServerConfiguration");
        beanDefinition.setInstanceSupplier(getEmbeddedWebServerSessionTimeoutInstanceSupplier());
        return beanDefinition;
      }
    }
  }
}
