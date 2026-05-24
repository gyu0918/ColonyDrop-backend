package org.springframework.session.data.redis.config.annotation.web.http;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.AutowiredMethodArgumentsResolver;
import org.springframework.beans.factory.support.RegisteredBean;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.SessionIdGenerator;

/**
 * Autowiring for {@link RedisHttpSessionConfiguration}.
 */
@Generated
public class RedisHttpSessionConfiguration__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static RedisHttpSessionConfiguration apply(RegisteredBean registeredBean,
      RedisHttpSessionConfiguration instance) {
    AutowiredMethodArgumentsResolver.forRequiredMethod("setRedisConnectionFactory", ObjectProvider.class, ObjectProvider.class).resolve(registeredBean, args -> instance.setRedisConnectionFactory(args.get(0), args.get(1)));
    AutowiredMethodArgumentsResolver.forMethod("setDefaultRedisSerializer", RedisSerializer.class).resolve(registeredBean, args -> instance.setDefaultRedisSerializer(args.get(0)));
    AutowiredMethodArgumentsResolver.forMethod("setSessionRepositoryCustomizer", ObjectProvider.class).resolve(registeredBean, args -> instance.setSessionRepositoryCustomizer(args.get(0)));
    AutowiredMethodArgumentsResolver.forMethod("setSessionIdGenerator", SessionIdGenerator.class).resolve(registeredBean, args -> instance.setSessionIdGenerator(args.get(0)));
    return instance;
  }
}
