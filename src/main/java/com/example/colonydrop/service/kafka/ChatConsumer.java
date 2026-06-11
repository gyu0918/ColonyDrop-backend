////package com.example.colonydrop.service.kafka;
////
////import com.example.colonydrop.dto.chat.ChatMessageDto;
////import com.fasterxml.jackson.core.JsonProcessingException;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import lombok.RequiredArgsConstructor;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.data.redis.core.StringRedisTemplate;
////import org.springframework.kafka.annotation.KafkaListener;
////import org.springframework.messaging.simp.SimpMessagingTemplate;
////import org.springframework.stereotype.Service;
////
////
////
////@Slf4j
////@Service
////@RequiredArgsConstructor
////public class ChatConsumer {
////
////    private final ObjectMapper objectMapper;
////    private final SimpMessagingTemplate simpMessagingTemplate;
////    private final StringRedisTemplate redisTemplate;  // 추가
////
////    @KafkaListener(topics = "chat-gundam", groupId = "colonydrop")
////    public void consumeGundam(String message) throws JsonProcessingException {
////        ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
////        log.info("[ChatConsumer] 건담채팅방 senderId={}, content={}", dto.getSenderId(), dto.getContent());
////
////        //웹소켓으로 보내는용
////        simpMessagingTemplate.convertAndSend("/topic/chat/gundam", dto);
////
////        //redis 에 저장
////        redisTemplate.opsForList().leftPush("chat:history:gundam", message);
////        redisTemplate.opsForList().trim("chat:history:gundam", 0, 99);
////    }
////
////    @KafkaListener(topics = "chat-openrun", groupId = "colonydrop")
////    public void consumeOpenrun(String message) throws JsonProcessingException {
////        ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
////        log.info("[ChatConsumer] 오픈런채팅방 senderId={}, content={}", dto.getSenderId(), dto.getContent());
////
////        //웹소켓으로 보내는용
////        simpMessagingTemplate.convertAndSend("/topic/chat/openrun", dto);
////
////        //redis 에 저장
////        redisTemplate.opsForList().leftPush("chat:history:openrun", message);
////        redisTemplate.opsForList().trim("chat:history:openrun", 0, 99);
////
////    }
////
////    @KafkaListener(topics = "chat-sharing", groupId = "colonydrop")
////    public void consumeSharing(String message) throws JsonProcessingException {
////        ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
////        log.info("[ChatConsumer] 나눔채팅방 senderId={}, content={}", dto.getSenderId(), dto.getContent());
////
////        //웹소켓으로 보내는용
////        simpMessagingTemplate.convertAndSend("/topic/chat/sharing", dto);
////
////        //redis 에 저장
////        redisTemplate.opsForList().leftPush("chat:history:sharing", message);
////        redisTemplate.opsForList().trim("chat:history:sharing", 0, 99);
////
////    }
////
////    @KafkaListener(topics = "chat-free", groupId = "colonydrop")
////    public void consumeFree(String message) throws JsonProcessingException {
////        ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
////        log.info("[ChatConsumer] 자유채팅방 senderId={}, content={}", dto.getSenderId(), dto.getContent());
////
////        //웹소켓으로 보내는용
////        simpMessagingTemplate.convertAndSend("/topic/chat/free", dto);
////
////        //redis 에 저장
////        redisTemplate.opsForList().leftPush("chat:history:free", message);
////        redisTemplate.opsForList().trim("chat:history:free", 0, 99);
////    }
////}
//
//package com.example.colonydrop.service.kafka;
//
//import com.example.colonydrop.config.redisConfig.RedisPublisher;
//import com.example.colonydrop.dto.chat.ChatMessageDto;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ChatConsumer {
//
//    private final ObjectMapper objectMapper;
//    private final RedisPublisher redisPublisher;
//    private final StringRedisTemplate redisTemplate;
//
//
//
//    @KafkaListener(topics = "chat-gundam", groupId = "colonydrop", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeGundam(String message) throws JsonProcessingException {
//        log.info("[ChatConsumer] 건담 수신 시각={}", java.time.LocalDateTime.now()); // ✅ 이것만 추가
//        ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
//        log.info("[ChatConsumer] 건담채팅방 senderId={}, content={}", dto.getSenderId(), dto.getContent());
//        redisPublisher.publishChat("gundam", dto);
//        redisTemplate.opsForList().leftPush("chat:history:gundam", message);
//        redisTemplate.opsForList().trim("chat:history:gundam", 0, 99);
//    }
//
//    @KafkaListener(topics = "chat-openrun", groupId = "colonydrop", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeOpenrun(String message) throws JsonProcessingException {
//        ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
//        log.info("[ChatConsumer] 오픈런채팅방 senderId={}, content={}", dto.getSenderId(), dto.getContent());
//        redisPublisher.publishChat("openrun", dto);
//        redisTemplate.opsForList().leftPush("chat:history:openrun", message);
//        redisTemplate.opsForList().trim("chat:history:openrun", 0, 99);
//    }
//
//    @KafkaListener(topics = "chat-sharing", groupId = "colonydrop", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeSharing(String message) throws JsonProcessingException {
//        ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
//        log.info("[ChatConsumer] 나눔채팅방 senderId={}, content={}", dto.getSenderId(), dto.getContent());
//        redisPublisher.publishChat("sharing", dto);
//        redisTemplate.opsForList().leftPush("chat:history:sharing", message);
//        redisTemplate.opsForList().trim("chat:history:sharing", 0, 99);
//    }
//
//    @KafkaListener(topics = "chat-free", groupId = "colonydrop", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeFree(String message) throws JsonProcessingException {
//        ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
//        log.info("[ChatConsumer] 자유채팅방 senderId={}, content={}", dto.getSenderId(), dto.getContent());
//        redisPublisher.publishChat("free", dto);
//        redisTemplate.opsForList().leftPush("chat:history:free", message);
//        redisTemplate.opsForList().trim("chat:history:free", 0, 99);
//    }
//}