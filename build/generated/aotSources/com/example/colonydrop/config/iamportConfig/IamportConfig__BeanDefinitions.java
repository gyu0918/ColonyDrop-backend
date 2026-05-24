package com.example.colonydrop.config.iamportConfig;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link IamportConfig}.
 */
@Generated
public class IamportConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'iamportConfig'.
   */
  public static BeanDefinition getIamportConfigBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(IamportConfig.class);
    beanDefinition.setTargetType(IamportConfig.class);
    ConfigurationClassUtils.initializeConfigurationClass(IamportConfig.class);
    InstanceSupplier<IamportConfig> instanceSupplier = InstanceSupplier.using(IamportConfig$$SpringCGLIB$$0::new);
    instanceSupplier = instanceSupplier.andThen(IamportConfig__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'iamportClient'.
   */
  private static BeanInstanceSupplier<IamportClient> getIamportClientInstanceSupplier() {
    return BeanInstanceSupplier.<IamportClient>forFactoryMethod(IamportConfig$$SpringCGLIB$$0.class, "iamportClient")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("iamportConfig", IamportConfig.class).iamportClient());
  }

  /**
   * Get the bean definition for 'iamportClient'.
   */
  public static BeanDefinition getIamportClientBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(IamportClient.class);
    beanDefinition.setFactoryBeanName("iamportConfig");
    beanDefinition.setInstanceSupplier(getIamportClientInstanceSupplier());
    return beanDefinition;
  }
}
