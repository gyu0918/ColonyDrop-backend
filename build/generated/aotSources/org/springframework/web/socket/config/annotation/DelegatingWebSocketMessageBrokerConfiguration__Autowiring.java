package org.springframework.web.socket.config.annotation;

import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredMethodArgumentsResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link DelegatingWebSocketMessageBrokerConfiguration}.
 */
@Generated
public class DelegatingWebSocketMessageBrokerConfiguration__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static DelegatingWebSocketMessageBrokerConfiguration apply(RegisteredBean registeredBean,
      DelegatingWebSocketMessageBrokerConfiguration instance) {
    AutowiredMethodArgumentsResolver.forMethod("setConfigurers", List.class).resolve(registeredBean, args -> instance.setConfigurers(args.get(0)));
    return instance;
  }
}
