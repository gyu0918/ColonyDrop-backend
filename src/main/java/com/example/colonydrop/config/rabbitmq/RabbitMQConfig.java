package com.example.colonydrop.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_QUEUE    = "order-queue";    // 큐 이름
    public static final String ORDER_EXCHANGE = "order-exchange";  // 라우터 이름
    public static final String ORDER_ROUTING  = "order-routing";  // 라우팅 키


    //메시지가 쌓이는 공간
    //durable = true → 서버 재시작해도 큐 유지
    //(false면 재시작 시 큐와 메시지 사라짐)
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE).build();
    }

    //Producer가 메시지를 Exchange에 보냄
    //Exchange는 라우팅 키를 보고 어느 큐로 보낼지 결정하는 라우터
    //Direct = 라우팅 키가 정확히 일치하는 큐로만 전달
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    //Exchange ↔ Queue 연결 설정
    //"order-routing" 키로 오는 메시지는 "order-queue"로 보내라
    @Bean
    public Binding orderBinding(Queue orderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderQueue)
                .to(orderExchange)
                .with(ORDER_ROUTING);
    }

    // Spring AMQP 4.x 호환 MessageConverter
//    Java 객체 ↔ RabbitMQ 메시지 변환기
//    SimpleMessageConverter = 기본 변환기
    @Bean
    public MessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }

//    Producer가 메시지를 보낼 때 사용하는 도구
//    Spring이 제공하는 RabbitMQ 전송 클라이언트
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    // 큐 관리 도구
    // 현재 큐에 메시지가 몇 개 있는지 조회할 때 사용
    // → 대기 순번 계산에 필요
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}