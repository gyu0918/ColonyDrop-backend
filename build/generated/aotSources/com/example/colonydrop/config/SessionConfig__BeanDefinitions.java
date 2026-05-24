package com.example.colonydrop.config;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.session.web.http.CookieSerializer;

/**
 * Bean definitions for {@link SessionConfig}.
 */
@Generated
public class SessionConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'sessionConfig'.
   */
  public static BeanDefinition getSessionConfigBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionConfig.class);
    beanDefinition.setTargetType(SessionConfig.class);
    ConfigurationClassUtils.initializeConfigurationClass(SessionConfig.class);
    beanDefinition.setInstanceSupplier(SessionConfig$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'cookieSerializer'.
   */
  private static BeanInstanceSupplier<CookieSerializer> getCookieSerializerInstanceSupplier() {
    return BeanInstanceSupplier.<CookieSerializer>forFactoryMethod(SessionConfig$$SpringCGLIB$$0.class, "cookieSerializer")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("sessionConfig", SessionConfig.class).cookieSerializer());
  }

  /**
   * Get the bean definition for 'cookieSerializer'.
   */
  public static BeanDefinition getCookieSerializerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CookieSerializer.class);
    beanDefinition.setFactoryBeanName("sessionConfig");
    beanDefinition.setInstanceSupplier(getCookieSerializerInstanceSupplier());
    return beanDefinition;
  }
}
