package org.springframework.web.socket.config.annotation;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link WebSocketMessageBrokerConfigurationSupport}.
 */
@Generated
public class WebSocketMessageBrokerConfigurationSupport__BeanDefinitions {
  /**
   * Get the bean definition for 'webSocketScopeConfigurer'.
   */
  public static BeanDefinition getWebSocketScopeConfigurerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(DelegatingWebSocketMessageBrokerConfiguration.class);
    beanDefinition.setTargetType(CustomScopeConfigurer.class);
    beanDefinition.setInstanceSupplier(BeanInstanceSupplier.<CustomScopeConfigurer>forFactoryMethod(WebSocketMessageBrokerConfigurationSupport.class, "webSocketScopeConfigurer").withGenerator((registeredBean) -> WebSocketMessageBrokerConfigurationSupport.webSocketScopeConfigurer()));
    return beanDefinition;
  }
}
