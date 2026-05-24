package org.springframework.boot.websocket.autoconfigure.servlet;

import java.util.Map;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.LazyInitializationExcludeFilter;
import tools.jackson.databind.json.JsonMapper;

/**
 * Bean definitions for {@link WebSocketMessagingAutoConfiguration}.
 */
@Generated
public class WebSocketMessagingAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'webSocketMessagingAutoConfiguration'.
   */
  public static BeanDefinition getWebSocketMessagingAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(WebSocketMessagingAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(WebSocketMessagingAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean definition for 'eagerStompWebSocketHandlerMapping'.
   */
  public static BeanDefinition getEagerStompWebSocketHandlerMappingBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(WebSocketMessagingAutoConfiguration.class);
    beanDefinition.setTargetType(LazyInitializationExcludeFilter.class);
    beanDefinition.setInstanceSupplier(BeanInstanceSupplier.<LazyInitializationExcludeFilter>forFactoryMethod(WebSocketMessagingAutoConfiguration.class, "eagerStompWebSocketHandlerMapping").withGenerator((registeredBean) -> WebSocketMessagingAutoConfiguration.eagerStompWebSocketHandlerMapping()));
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'springBootWebSocketMessageBrokerConfigurer'.
   */
  private static BeanInstanceSupplier<WebSocketMessagingAutoConfiguration.SpringBootWebSocketMessageBrokerConfigurer> getSpringBootWebSocketMessageBrokerConfigurerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<WebSocketMessagingAutoConfiguration.SpringBootWebSocketMessageBrokerConfigurer>forFactoryMethod(WebSocketMessagingAutoConfiguration.class, "springBootWebSocketMessageBrokerConfigurer", Map.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.websocket.autoconfigure.servlet.WebSocketMessagingAutoConfiguration", WebSocketMessagingAutoConfiguration.class).springBootWebSocketMessageBrokerConfigurer(args.get(0)));
  }

  /**
   * Get the bean definition for 'springBootWebSocketMessageBrokerConfigurer'.
   */
  public static BeanDefinition getSpringBootWebSocketMessageBrokerConfigurerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(WebSocketMessagingAutoConfiguration.SpringBootWebSocketMessageBrokerConfigurer.class);
    beanDefinition.setFactoryBeanName("org.springframework.boot.websocket.autoconfigure.servlet.WebSocketMessagingAutoConfiguration");
    beanDefinition.setInstanceSupplier(getSpringBootWebSocketMessageBrokerConfigurerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link WebSocketMessagingAutoConfiguration.JacksonWebSocketMessageConverterConfiguration}.
   */
  @Generated
  public static class JacksonWebSocketMessageConverterConfiguration {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.websocket.autoconfigure.servlet.WebSocketMessagingAutoConfiguration$JacksonWebSocketMessageConverterConfiguration'.
     */
    private static BeanInstanceSupplier<WebSocketMessagingAutoConfiguration.JacksonWebSocketMessageConverterConfiguration> getJacksonWebSocketMessageConverterConfigurationInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<WebSocketMessagingAutoConfiguration.JacksonWebSocketMessageConverterConfiguration>forConstructor(JsonMapper.class)
              .withGenerator((registeredBean, args) -> new WebSocketMessagingAutoConfiguration.JacksonWebSocketMessageConverterConfiguration(args.get(0)));
    }

    /**
     * Get the bean definition for 'jacksonWebSocketMessageConverterConfiguration'.
     */
    public static BeanDefinition getJacksonWebSocketMessageConverterConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(WebSocketMessagingAutoConfiguration.JacksonWebSocketMessageConverterConfiguration.class);
      beanDefinition.setInstanceSupplier(getJacksonWebSocketMessageConverterConfigurationInstanceSupplier());
      return beanDefinition;
    }
  }
}
