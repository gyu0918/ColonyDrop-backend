package com.example.colonydrop.config.iamportConfig;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link IamportConfig}.
 */
@Generated
public class IamportConfig__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static IamportConfig apply(RegisteredBean registeredBean, IamportConfig instance) {
    AutowiredFieldValueResolver.forRequiredField("apiKey").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("apiSecret").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
