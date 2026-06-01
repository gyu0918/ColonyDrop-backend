package com.example.colonydrop.service.support;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SlackNotificationService {

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    private final RestTemplate restTemplate;

    public void sendSupportNotification(String memberName, String title) {
        String message = String.format(
                "📩 새 문의가 등록됐습니다!\n작성자: %s\n제목: %s",
                memberName, title
        );

        restTemplate.postForEntity(
                webhookUrl,
                Map.of("text", message),
                String.class
        );
    }
}
