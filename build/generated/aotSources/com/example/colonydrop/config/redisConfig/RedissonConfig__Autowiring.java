package com.example.colonydrop.config.redisConfig;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link RedissonConfig}.
 */
@Generated
public class RedissonConfig__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static RedissonConfig apply(RegisteredBean registeredBean, RedissonConfig instance) {
    AutowiredFieldValueResolver.forRequiredField("redisHost").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("redisPort").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
