package com.example.colonydrop.config.websocket;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link WebSocketConfig}.
 */
@Generated
public class WebSocketConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'webSocketConfig'.
   */
  public static BeanDefinition getWebSocketConfigBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(WebSocketConfig.class);
    beanDefinition.setTargetType(WebSocketConfig.class);
    ConfigurationClassUtils.initializeConfigurationClass(WebSocketConfig.class);
    beanDefinition.setInstanceSupplier(WebSocketConfig$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
