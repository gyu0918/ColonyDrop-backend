package com.example.colonydrop.service.order;

import com.example.colonydrop.dto.payment.OrderCreateRequest;
import com.example.colonydrop.entity.item.Item;
import com.example.colonydrop.entity.member.Member;
import com.example.colonydrop.entity.order.Order;
import com.example.colonydrop.repository.item.ItemRepository;
import com.example.colonydrop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final RedissonClient redissonClient;
    // 주문 생성 메서드
    // 파라미터: OrderCreateRequest, Member
    // 반환: Order
    @Transactional
    public Order createOrder(Member member, OrderCreateRequest orderCreateRequest) {

        // 분산 락 키 (상품 ID 기준)
        String lockKey = "item:lock:" + orderCreateRequest.getItemId();
        RLock lock = redissonClient.getLock(lockKey);
        try{
            // 락 획득 시도
            // waitTime: 락 획득 최대 대기 시간 (5초)
            // leaseTime: 락 자동 해제 시간 (3초) → 데드락 방지
            boolean isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);

//            // 1. 상품 조회
//            Item item = itemRepository.findById(orderCreateRequest.getItemId()).orElseThrow(() -> new IllegalArgumentException("상품을 찾을수 없습니다요"));

            // 1. 상품 조회 (일반 조회로 변경 - 분산 락이 동시성 보장)
            Item item = itemRepository.findById(orderCreateRequest.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

            // 2. 상품 판매 가능 여부 확인 (SALE 상태인지)
            if (!"SALE".equals(item.getStatus())){
                throw new IllegalArgumentException("품절 되었습니다");
            }
            item.setStatus("RESERVED"); // 다른 사람이 주문 못하게 막기

            // 3. merchant_uid 생성 (중복 불가 주문번호)
            String merchant_uid = createMerchantUid();

            // 4. Order 빌더로 생성 (PENDING 상태)
            Order order = Order.builder().merchantUid(merchant_uid)
                    .buyer(member)
                    .item(item)
                    .totalPrice(item.getPrice())
                    .status("PENDING")
                    .build();

            // 5. DB 저장 후 반환
            return orderRepository.save(order);

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new IllegalStateException("주문 처리 중 오류가 발생했습니다.");
        }finally {
            // 락 해제 (락 보유자만 해제 가능)
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }


    }

    // CD-20260502153045-A3F9K2 형식
    private String createMerchantUid() {
        String datePart = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomPart = generateRandomCode(6);
        return String.format("CD-%s-%s", datePart, randomPart);
    }

    private String generateRandomCode(int length) {
        return new SecureRandom()
                .ints(length, 0, 36)
                .mapToObj(i -> Integer.toString(i, 36).toUpperCase())
                .collect(Collectors.joining());
    }

}
