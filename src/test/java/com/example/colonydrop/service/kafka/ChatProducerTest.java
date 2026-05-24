//package com.example.colonydrop.service.kafka;
//
//import com.example.colonydrop.service.kafka.ChatProducer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class ChatProducerTest {
//
//
//    @Mock
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @InjectMocks
//    private ChatProducer chatProducer;
//
//    @Test
//    void 건담_채팅방_메시지_전송_테스트() {
//        // given
//        String topic = "chat-gundam";
//        String message = "안녕하세요";
//
//        // when
//        chatProducer.sendMessage(topic, message);
//
//        // then
//        verify(kafkaTemplate, times(1)).send(eq(topic), eq(message));
//    }
//
//    @Test
//    void 오픈런_채팅방_메시지_전송_테스트() {
//        // given
//        String topic = "chat-openrun";
//        String message = "오픈런 몇시에 시작하나요?";
//
//        // when
//        chatProducer.sendMessage(topic, message);
//
//        // then
//        verify(kafkaTemplate, times(1)).send(eq(topic), eq(message));
//    }
//
//    @Test
//    void 나눔채팅방(){
//        //given
//        String topic = "chat-sharing";
//        String message = "나눔할게요 모이세요";
//
//        //when
//        chatProducer.sendMessage(topic, message);
//
//        //then
//        verify(kafkaTemplate, times(1)).send(eq(topic), eq(message));
//    }
//}
