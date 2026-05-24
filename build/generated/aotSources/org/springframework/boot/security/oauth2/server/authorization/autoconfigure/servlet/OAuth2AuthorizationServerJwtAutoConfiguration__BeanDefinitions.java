package org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/**
 * Bean definitions for {@link OAuth2AuthorizationServerJwtAutoConfiguration}.
 */
@Generated
public class OAuth2AuthorizationServerJwtAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'oAuth2AuthorizationServerJwtAutoConfiguration'.
   */
  public static BeanDefinition getOAuthAuthorizationServerJwtAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2AuthorizationServerJwtAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(OAuth2AuthorizationServerJwtAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'jwkSource'.
   */
  private static BeanInstanceSupplier<JWKSource> getJwkSourceInstanceSupplier() {
    return BeanInstanceSupplier.<JWKSource>forFactoryMethod(OAuth2AuthorizationServerJwtAutoConfiguration.class, "jwkSource")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerJwtAutoConfiguration", OAuth2AuthorizationServerJwtAutoConfiguration.class).jwkSource());
  }

  /**
   * Get the bean definition for 'jwkSource'.
   */
  public static BeanDefinition getJwkSourceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(JWKSource.class);
    beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(JWKSource.class, SecurityContext.class));
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setFactoryBeanName("org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerJwtAutoConfiguration");
    beanDefinition.setInstanceSupplier(getJwkSourceInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link OAuth2AuthorizationServerJwtAutoConfiguration.JwtDecoderConfiguration}.
   */
  @Generated
  public static class JwtDecoderConfiguration {
    /**
     * Get the bean definition for 'jwtDecoderConfiguration'.
     */
    public static BeanDefinition getJwtDecoderConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2AuthorizationServerJwtAutoConfiguration.JwtDecoderConfiguration.class);
      beanDefinition.setInstanceSupplier(OAuth2AuthorizationServerJwtAutoConfiguration.JwtDecoderConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jwtDecoder'.
     */
    private static BeanInstanceSupplier<JwtDecoder> getJwtDecoderInstanceSupplier() {
      return BeanInstanceSupplier.<JwtDecoder>forFactoryMethod(OAuth2AuthorizationServerJwtAutoConfiguration.JwtDecoderConfiguration.class, "jwtDecoder", JWKSource.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerJwtAutoConfiguration$JwtDecoderConfiguration", OAuth2AuthorizationServerJwtAutoConfiguration.JwtDecoderConfiguration.class).jwtDecoder(args.get(0)));
    }

    /**
     * Get the bean definition for 'jwtDecoder'.
     */
    public static BeanDefinition getJwtDecoderBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(JwtDecoder.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerJwtAutoConfiguration$JwtDecoderConfiguration");
      beanDefinition.setInstanceSupplier(getJwtDecoderInstanceSupplier());
      return beanDefinition;
    }
  }
}
