package org.mybatis.spring.boot.autoconfigure;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.io.ResourceLoader;

/**
 * Bean definitions for {@link MybatisAutoConfiguration}.
 */
@Generated
public class MybatisAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration'.
   */
  private static BeanInstanceSupplier<MybatisAutoConfiguration> getMybatisAutoConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<MybatisAutoConfiguration>forConstructor(MybatisProperties.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ResourceLoader.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class)
            .withGenerator((registeredBean, args) -> new MybatisAutoConfiguration(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6), args.get(7)));
  }

  /**
   * Get the bean definition for 'mybatisAutoConfiguration'.
   */
  public static BeanDefinition getMybatisAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MybatisAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(getMybatisAutoConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'sqlSessionFactory'.
   */
  private static BeanInstanceSupplier<SqlSessionFactory> getSqlSessionFactoryInstanceSupplier() {
    return BeanInstanceSupplier.<SqlSessionFactory>forFactoryMethod(MybatisAutoConfiguration.class, "sqlSessionFactory", DataSource.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration", MybatisAutoConfiguration.class).sqlSessionFactory(args.get(0)));
  }

  /**
   * Get the bean definition for 'sqlSessionFactory'.
   */
  public static BeanDefinition getSqlSessionFactoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SqlSessionFactory.class);
    beanDefinition.setFactoryBeanName("org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration");
    beanDefinition.setInstanceSupplier(getSqlSessionFactoryInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'sqlSessionTemplate'.
   */
  private static BeanInstanceSupplier<SqlSessionTemplate> getSqlSessionTemplateInstanceSupplier() {
    return BeanInstanceSupplier.<SqlSessionTemplate>forFactoryMethod(MybatisAutoConfiguration.class, "sqlSessionTemplate", SqlSessionFactory.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration", MybatisAutoConfiguration.class).sqlSessionTemplate(args.get(0)));
  }

  /**
   * Get the bean definition for 'sqlSessionTemplate'.
   */
  public static BeanDefinition getSqlSessionTemplateBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SqlSessionTemplate.class);
    beanDefinition.setDependsOn("dataSourceScriptDatabaseInitializer");
    beanDefinition.setFactoryBeanName("org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration");
    beanDefinition.setInstanceSupplier(getSqlSessionTemplateInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link MybatisAutoConfiguration.MapperScannerRegistrarNotFoundConfiguration}.
   */
  @Generated
  public static class MapperScannerRegistrarNotFoundConfiguration {
    /**
     * Get the bean definition for 'mapperScannerRegistrarNotFoundConfiguration'.
     */
    public static BeanDefinition getMapperScannerRegistrarNotFoundConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(MybatisAutoConfiguration.MapperScannerRegistrarNotFoundConfiguration.class);
      beanDefinition.setInstanceSupplier(MybatisAutoConfiguration.MapperScannerRegistrarNotFoundConfiguration::new);
      return beanDefinition;
    }
  }
}
