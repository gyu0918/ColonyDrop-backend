package com.example.colonydrop.controller.refreshToken;

import com.example.colonydrop.config.security.oauth2.JwtProperties;
import com.example.colonydrop.repository.member.MemberRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Bean definitions for {@link RefreshTokenController}.
 */
@Generated
public class RefreshTokenController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'refreshTokenController'.
   */
  private static BeanInstanceSupplier<RefreshTokenController> getRefreshTokenControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<RefreshTokenController>forConstructor(JwtProperties.class, StringRedisTemplate.class, BCryptPasswordEncoder.class, MemberRepository.class)
            .withGenerator((registeredBean, args) -> new RefreshTokenController(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'refreshTokenController'.
   */
  public static BeanDefinition getRefreshTokenControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RefreshTokenController.class);
    beanDefinition.setInstanceSupplier(getRefreshTokenControllerInstanceSupplier());
    return beanDefinition;
  }
}
