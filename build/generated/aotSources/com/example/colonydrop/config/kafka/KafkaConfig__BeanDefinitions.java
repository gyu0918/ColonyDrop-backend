package com.example.colonydrop.config.kafka;

import java.lang.String;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.core.ResolvableType;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * Bean definitions for {@link KafkaConfig}.
 */
@Generated
public class KafkaConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'kafkaConfig'.
   */
  public static BeanDefinition getKafkaConfigBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(KafkaConfig.class);
    beanDefinition.setTargetType(KafkaConfig.class);
    ConfigurationClassUtils.initializeConfigurationClass(KafkaConfig.class);
    beanDefinition.setInstanceSupplier(KafkaConfig$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'producerFactory'.
   */
  private static BeanInstanceSupplier<ProducerFactory> getProducerFactoryInstanceSupplier() {
    return BeanInstanceSupplier.<ProducerFactory>forFactoryMethod(KafkaConfig$$SpringCGLIB$$0.class, "producerFactory")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("kafkaConfig", KafkaConfig.class).producerFactory());
  }

  /**
   * Get the bean definition for 'producerFactory'.
   */
  public static BeanDefinition getProducerFactoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ProducerFactory.class);
    beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(ProducerFactory.class, String.class, String.class));
    beanDefinition.setFactoryBeanName("kafkaConfig");
    beanDefinition.setInstanceSupplier(getProducerFactoryInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'kafkaTemplate'.
   */
  private static BeanInstanceSupplier<KafkaTemplate> getKafkaTemplateInstanceSupplier() {
    return BeanInstanceSupplier.<KafkaTemplate>forFactoryMethod(KafkaConfig$$SpringCGLIB$$0.class, "kafkaTemplate")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("kafkaConfig", KafkaConfig.class).kafkaTemplate());
  }

  /**
   * Get the bean definition for 'kafkaTemplate'.
   */
  public static BeanDefinition getKafkaTemplateBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(KafkaTemplate.class);
    beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(KafkaTemplate.class, String.class, String.class));
    beanDefinition.setFactoryBeanName("kafkaConfig");
    beanDefinition.setInstanceSupplier(getKafkaTemplateInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'consumerFactory'.
   */
  private static BeanInstanceSupplier<ConsumerFactory> getConsumerFactoryInstanceSupplier() {
    return BeanInstanceSupplier.<ConsumerFactory>forFactoryMethod(KafkaConfig$$SpringCGLIB$$0.class, "consumerFactory")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("kafkaConfig", KafkaConfig.class).consumerFactory());
  }

  /**
   * Get the bean definition for 'consumerFactory'.
   */
  public static BeanDefinition getConsumerFactoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ConsumerFactory.class);
    beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(ConsumerFactory.class, String.class, String.class));
    beanDefinition.setFactoryBeanName("kafkaConfig");
    beanDefinition.setInstanceSupplier(getConsumerFactoryInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'kafkaListenerContainerFactory'.
   */
  private static BeanInstanceSupplier<ConcurrentKafkaListenerContainerFactory> getKafkaListenerContainerFactoryInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ConcurrentKafkaListenerContainerFactory>forFactoryMethod(KafkaConfig$$SpringCGLIB$$0.class, "kafkaListenerContainerFactory")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("kafkaConfig", KafkaConfig.class).kafkaListenerContainerFactory());
  }

  /**
   * Get the bean definition for 'kafkaListenerContainerFactory'.
   */
  public static BeanDefinition getKafkaListenerContainerFactoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ConcurrentKafkaListenerContainerFactory.class);
    beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(ConcurrentKafkaListenerContainerFactory.class, String.class, String.class));
    beanDefinition.setFactoryBeanName("kafkaConfig");
    beanDefinition.setInstanceSupplier(getKafkaListenerContainerFactoryInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'gundamChat'.
   */
  private static BeanInstanceSupplier<NewTopic> getGundamChatInstanceSupplier() {
    return BeanInstanceSupplier.<NewTopic>forFactoryMethod(KafkaConfig$$SpringCGLIB$$0.class, "gundamChat")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("kafkaConfig", KafkaConfig.class).gundamChat());
  }

  /**
   * Get the bean definition for 'gundamChat'.
   */
  public static BeanDefinition getGundamChatBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(NewTopic.class);
    beanDefinition.setFactoryBeanName("kafkaConfig");
    beanDefinition.setInstanceSupplier(getGundamChatInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'openrunChat'.
   */
  private static BeanInstanceSupplier<NewTopic> getOpenrunChatInstanceSupplier() {
    return BeanInstanceSupplier.<NewTopic>forFactoryMethod(KafkaConfig$$SpringCGLIB$$0.class, "openrunChat")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("kafkaConfig", KafkaConfig.class).openrunChat());
  }

  /**
   * Get the bean definition for 'openrunChat'.
   */
  public static BeanDefinition getOpenrunChatBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(NewTopic.class);
    beanDefinition.setFactoryBeanName("kafkaConfig");
    beanDefinition.setInstanceSupplier(getOpenrunChatInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'sharingChat'.
   */
  private static BeanInstanceSupplier<NewTopic> getSharingChatInstanceSupplier() {
    return BeanInstanceSupplier.<NewTopic>forFactoryMethod(KafkaConfig$$SpringCGLIB$$0.class, "sharingChat")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("kafkaConfig", KafkaConfig.class).sharingChat());
  }

  /**
   * Get the bean definition for 'sharingChat'.
   */
  public static BeanDefinition getSharingChatBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(NewTopic.class);
    beanDefinition.setFactoryBeanName("kafkaConfig");
    beanDefinition.setInstanceSupplier(getSharingChatInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'freeChat'.
   */
  private static BeanInstanceSupplier<NewTopic> getFreeChatInstanceSupplier() {
    return BeanInstanceSupplier.<NewTopic>forFactoryMethod(KafkaConfig$$SpringCGLIB$$0.class, "freeChat")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("kafkaConfig", KafkaConfig.class).freeChat());
  }

  /**
   * Get the bean definition for 'freeChat'.
   */
  public static BeanDefinition getFreeChatBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(NewTopic.class);
    beanDefinition.setFactoryBeanName("kafkaConfig");
    beanDefinition.setInstanceSupplier(getFreeChatInstanceSupplier());
    return beanDefinition;
  }
}
