package com.example.colonydrop.config.websocket;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Bean definitions for {@link WebSocketEventListener}.
 */
@Generated
public class WebSocketEventListener__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'webSocketEventListener'.
   */
  private static BeanInstanceSupplier<WebSocketEventListener> getWebSocketEventListenerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<WebSocketEventListener>forConstructor(StringRedisTemplate.class, SimpMessagingTemplate.class)
            .withGenerator((registeredBean, args) -> new WebSocketEventListener(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'webSocketEventListener'.
   */
  public static BeanDefinition getWebSocketEventListenerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(WebSocketEventListener.class);
    beanDefinition.setInstanceSupplier(getWebSocketEventListenerInstanceSupplier());
    return beanDefinition;
  }
}
