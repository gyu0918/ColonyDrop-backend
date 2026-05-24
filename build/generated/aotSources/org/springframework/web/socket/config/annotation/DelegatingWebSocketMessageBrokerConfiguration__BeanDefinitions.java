package org.springframework.web.socket.config.annotation;

import java.util.concurrent.Executor;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.broker.AbstractBrokerMessageHandler;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationMessageHandler;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

/**
 * Bean definitions for {@link DelegatingWebSocketMessageBrokerConfiguration}.
 */
@Generated
public class DelegatingWebSocketMessageBrokerConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'delegatingWebSocketMessageBrokerConfiguration'.
   */
  public static BeanDefinition getDelegatingWebSocketMessageBrokerConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(DelegatingWebSocketMessageBrokerConfiguration.class);
    InstanceSupplier<DelegatingWebSocketMessageBrokerConfiguration> instanceSupplier = InstanceSupplier.using(DelegatingWebSocketMessageBrokerConfiguration::new);
    instanceSupplier = instanceSupplier.andThen(DelegatingWebSocketMessageBrokerConfiguration__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'stompWebSocketHandlerMapping'.
   */
  private static BeanInstanceSupplier<HandlerMapping> getStompWebSocketHandlerMappingInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<HandlerMapping>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "stompWebSocketHandlerMapping", WebSocketHandler.class, TaskScheduler.class, AbstractSubscribableChannel.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).stompWebSocketHandlerMapping(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'stompWebSocketHandlerMapping'.
   */
  public static BeanDefinition getStompWebSocketHandlerMappingBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HandlerMapping.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getStompWebSocketHandlerMappingInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'subProtocolWebSocketHandler'.
   */
  private static BeanInstanceSupplier<WebSocketHandler> getSubProtocolWebSocketHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<WebSocketHandler>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "subProtocolWebSocketHandler", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).subProtocolWebSocketHandler(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'subProtocolWebSocketHandler'.
   */
  public static BeanDefinition getSubProtocolWebSocketHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(WebSocketHandler.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getSubProtocolWebSocketHandlerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'webSocketMessageBrokerStats'.
   */
  private static BeanInstanceSupplier<WebSocketMessageBrokerStats> getWebSocketMessageBrokerStatsInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<WebSocketMessageBrokerStats>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "webSocketMessageBrokerStats", AbstractBrokerMessageHandler.class, WebSocketHandler.class, TaskExecutor.class, TaskExecutor.class, TaskScheduler.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).webSocketMessageBrokerStats(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'webSocketMessageBrokerStats'.
   */
  public static BeanDefinition getWebSocketMessageBrokerStatsBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(WebSocketMessageBrokerStats.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getWebSocketMessageBrokerStatsInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientInboundChannel'.
   */
  private static BeanInstanceSupplier<AbstractSubscribableChannel> getClientInboundChannelInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AbstractSubscribableChannel>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "clientInboundChannel", Executor.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).clientInboundChannel(args.get(0)));
  }

  /**
   * Get the bean definition for 'clientInboundChannel'.
   */
  public static BeanDefinition getClientInboundChannelBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AbstractSubscribableChannel.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getClientInboundChannelInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientInboundChannelExecutor'.
   */
  private static BeanInstanceSupplier<Executor> getClientInboundChannelExecutorInstanceSupplier() {
    return BeanInstanceSupplier.<Executor>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "clientInboundChannelExecutor")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).clientInboundChannelExecutor());
  }

  /**
   * Get the bean definition for 'clientInboundChannelExecutor'.
   */
  public static BeanDefinition getClientInboundChannelExecutorBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(Executor.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getClientInboundChannelExecutorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientOutboundChannel'.
   */
  private static BeanInstanceSupplier<AbstractSubscribableChannel> getClientOutboundChannelInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AbstractSubscribableChannel>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "clientOutboundChannel", Executor.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).clientOutboundChannel(args.get(0)));
  }

  /**
   * Get the bean definition for 'clientOutboundChannel'.
   */
  public static BeanDefinition getClientOutboundChannelBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AbstractSubscribableChannel.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getClientOutboundChannelInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientOutboundChannelExecutor'.
   */
  private static BeanInstanceSupplier<Executor> getClientOutboundChannelExecutorInstanceSupplier() {
    return BeanInstanceSupplier.<Executor>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "clientOutboundChannelExecutor")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).clientOutboundChannelExecutor());
  }

  /**
   * Get the bean definition for 'clientOutboundChannelExecutor'.
   */
  public static BeanDefinition getClientOutboundChannelExecutorBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(Executor.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getClientOutboundChannelExecutorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'brokerChannel'.
   */
  private static BeanInstanceSupplier<AbstractSubscribableChannel> getBrokerChannelInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AbstractSubscribableChannel>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "brokerChannel", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, Executor.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).brokerChannel(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'brokerChannel'.
   */
  public static BeanDefinition getBrokerChannelBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AbstractSubscribableChannel.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getBrokerChannelInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'brokerChannelExecutor'.
   */
  private static BeanInstanceSupplier<Executor> getBrokerChannelExecutorInstanceSupplier() {
    return BeanInstanceSupplier.<Executor>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "brokerChannelExecutor", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).brokerChannelExecutor(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'brokerChannelExecutor'.
   */
  public static BeanDefinition getBrokerChannelExecutorBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(Executor.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getBrokerChannelExecutorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'simpAnnotationMethodMessageHandler'.
   */
  private static BeanInstanceSupplier<SimpAnnotationMethodMessageHandler> getSimpAnnotationMethodMessageHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SimpAnnotationMethodMessageHandler>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "simpAnnotationMethodMessageHandler", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, SimpMessagingTemplate.class, CompositeMessageConverter.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).simpAnnotationMethodMessageHandler(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'simpAnnotationMethodMessageHandler'.
   */
  public static BeanDefinition getSimpAnnotationMethodMessageHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SimpAnnotationMethodMessageHandler.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getSimpAnnotationMethodMessageHandlerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'simpleBrokerMessageHandler'.
   */
  private static BeanInstanceSupplier<AbstractBrokerMessageHandler> getSimpleBrokerMessageHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AbstractBrokerMessageHandler>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "simpleBrokerMessageHandler", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, UserDestinationResolver.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).simpleBrokerMessageHandler(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'simpleBrokerMessageHandler'.
   */
  public static BeanDefinition getSimpleBrokerMessageHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AbstractBrokerMessageHandler.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getSimpleBrokerMessageHandlerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'stompBrokerRelayMessageHandler'.
   */
  private static BeanInstanceSupplier<AbstractBrokerMessageHandler> getStompBrokerRelayMessageHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AbstractBrokerMessageHandler>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "stompBrokerRelayMessageHandler", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, UserDestinationMessageHandler.class, MessageHandler.class, UserDestinationResolver.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).stompBrokerRelayMessageHandler(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5)));
  }

  /**
   * Get the bean definition for 'stompBrokerRelayMessageHandler'.
   */
  public static BeanDefinition getStompBrokerRelayMessageHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AbstractBrokerMessageHandler.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getStompBrokerRelayMessageHandlerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'userDestinationMessageHandler'.
   */
  private static BeanInstanceSupplier<UserDestinationMessageHandler> getUserDestinationMessageHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<UserDestinationMessageHandler>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "userDestinationMessageHandler", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, UserDestinationResolver.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).userDestinationMessageHandler(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'userDestinationMessageHandler'.
   */
  public static BeanDefinition getUserDestinationMessageHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(UserDestinationMessageHandler.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getUserDestinationMessageHandlerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'userRegistryMessageHandler'.
   */
  private static BeanInstanceSupplier<MessageHandler> getUserRegistryMessageHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<MessageHandler>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "userRegistryMessageHandler", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, SimpUserRegistry.class, SimpMessagingTemplate.class, TaskScheduler.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).userRegistryMessageHandler(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'userRegistryMessageHandler'.
   */
  public static BeanDefinition getUserRegistryMessageHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MessageHandler.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getUserRegistryMessageHandlerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'messageBrokerTaskScheduler'.
   */
  private static BeanInstanceSupplier<TaskScheduler> getMessageBrokerTaskSchedulerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<TaskScheduler>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "messageBrokerTaskScheduler")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).messageBrokerTaskScheduler());
  }

  /**
   * Get the bean definition for 'messageBrokerTaskScheduler'.
   */
  public static BeanDefinition getMessageBrokerTaskSchedulerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(TaskScheduler.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getMessageBrokerTaskSchedulerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'brokerMessagingTemplate'.
   */
  private static BeanInstanceSupplier<SimpMessagingTemplate> getBrokerMessagingTemplateInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SimpMessagingTemplate>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "brokerMessagingTemplate", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, AbstractSubscribableChannel.class, CompositeMessageConverter.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).brokerMessagingTemplate(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'brokerMessagingTemplate'.
   */
  public static BeanDefinition getBrokerMessagingTemplateBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SimpMessagingTemplate.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getBrokerMessagingTemplateInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'brokerMessageConverter'.
   */
  private static BeanInstanceSupplier<CompositeMessageConverter> getBrokerMessageConverterInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<CompositeMessageConverter>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "brokerMessageConverter")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).brokerMessageConverter());
  }

  /**
   * Get the bean definition for 'brokerMessageConverter'.
   */
  public static BeanDefinition getBrokerMessageConverterBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CompositeMessageConverter.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getBrokerMessageConverterInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'userDestinationResolver'.
   */
  private static BeanInstanceSupplier<UserDestinationResolver> getUserDestinationResolverInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<UserDestinationResolver>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "userDestinationResolver", SimpUserRegistry.class, AbstractSubscribableChannel.class, AbstractSubscribableChannel.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).userDestinationResolver(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'userDestinationResolver'.
   */
  public static BeanDefinition getUserDestinationResolverBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(UserDestinationResolver.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getUserDestinationResolverInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'userRegistry'.
   */
  private static BeanInstanceSupplier<SimpUserRegistry> getUserRegistryInstanceSupplier() {
    return BeanInstanceSupplier.<SimpUserRegistry>forFactoryMethod(DelegatingWebSocketMessageBrokerConfiguration.class, "userRegistry", AbstractSubscribableChannel.class, AbstractSubscribableChannel.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration", DelegatingWebSocketMessageBrokerConfiguration.class).userRegistry(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'userRegistry'.
   */
  public static BeanDefinition getUserRegistryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SimpUserRegistry.class);
    beanDefinition.setFactoryBeanName("org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration");
    beanDefinition.setInstanceSupplier(getUserRegistryInstanceSupplier());
    return beanDefinition;
  }
}
