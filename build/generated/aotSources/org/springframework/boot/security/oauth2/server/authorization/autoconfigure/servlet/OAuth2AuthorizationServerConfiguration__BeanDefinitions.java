package org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

/**
 * Bean definitions for {@link OAuth2AuthorizationServerConfiguration}.
 */
@Generated
public class OAuth2AuthorizationServerConfiguration__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerConfiguration'.
   */
  private static BeanInstanceSupplier<OAuth2AuthorizationServerConfiguration> getOAuthAuthorizationServerConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<OAuth2AuthorizationServerConfiguration>forConstructor(OAuth2AuthorizationServerProperties.class)
            .withGenerator((registeredBean, args) -> new OAuth2AuthorizationServerConfiguration(args.get(0)));
  }

  /**
   * Get the bean definition for 'oAuth2AuthorizationServerConfiguration'.
   */
  public static BeanDefinition getOAuthAuthorizationServerConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2AuthorizationServerConfiguration.class);
    beanDefinition.setInstanceSupplier(getOAuthAuthorizationServerConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'authorizationServerSettings'.
   */
  private static BeanInstanceSupplier<AuthorizationServerSettings> getAuthorizationServerSettingsInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AuthorizationServerSettings>forFactoryMethod(OAuth2AuthorizationServerConfiguration.class, "authorizationServerSettings")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerConfiguration", OAuth2AuthorizationServerConfiguration.class).authorizationServerSettings());
  }

  /**
   * Get the bean definition for 'authorizationServerSettings'.
   */
  public static BeanDefinition getAuthorizationServerSettingsBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AuthorizationServerSettings.class);
    beanDefinition.setFactoryBeanName("org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerConfiguration");
    beanDefinition.setInstanceSupplier(getAuthorizationServerSettingsInstanceSupplier());
    return beanDefinition;
  }
}
