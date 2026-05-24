package com.example.colonydrop.config.security.auth;

import com.example.colonydrop.repository.member.MemberRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PrincipalDetailsService}.
 */
@Generated
public class PrincipalDetailsService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'principalDetailsService'.
   */
  private static BeanInstanceSupplier<PrincipalDetailsService> getPrincipalDetailsServiceInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<PrincipalDetailsService>forConstructor(MemberRepository.class)
            .withGenerator((registeredBean, args) -> new PrincipalDetailsService(args.get(0)));
  }

  /**
   * Get the bean definition for 'principalDetailsService'.
   */
  public static BeanDefinition getPrincipalDetailsServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PrincipalDetailsService.class);
    beanDefinition.setInstanceSupplier(getPrincipalDetailsServiceInstanceSupplier());
    return beanDefinition;
  }
}
