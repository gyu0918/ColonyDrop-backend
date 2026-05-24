package com.example.colonydrop.service.kafka;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ChatConsumerTest {

    @InjectMocks
    private ChatConsumer chatConsumer;

    @Test
    void 건담_채팅방_메시지_수신_테스트() {
        // given
        String message = "안녕하세요";

        // when & then
        assertDoesNotThrow(() -> chatConsumer.consumeGundam(message));
    }

    @Test
    void 오픈런_채팅방_메시지_수신_테스트() {
        // given
        String message = "오픈런 몇시에요?";

        // when & then
        assertDoesNotThrow(() -> chatConsumer.consumeOpenrun(message));
    }

    @Test
    void 나눔_채팅방_메시지_수신_테스트() {
        // given
        String message = "나눔할게요";

        // when & then
        assertDoesNotThrow(() -> chatConsumer.consumeSharing(message));
    }

    @Test
    void 자유_채팅방_메시지_수신_테스트() {
        // given
        String message = "자유롭게 얘기해요";

        // when & then
        assertDoesNotThrow(() -> chatConsumer.consumeFree(message));
    }


}
