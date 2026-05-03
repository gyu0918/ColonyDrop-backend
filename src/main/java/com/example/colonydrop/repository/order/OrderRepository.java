package com.example.colonydrop.repository.order;

import com.example.colonydrop.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // fetch join으로 item, buyer 한번에 조회 → N+1 방지
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.item " +
            "JOIN FETCH o.buyer " +
            "WHERE o.merchantUid = :merchantUid")
    Optional<Order> findByMerchantUid(@Param("merchantUid") String merchantUid);

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.item " +
            "JOIN FETCH o.buyer " +
            "WHERE o.impUid = :impUid")
    Optional<Order> findByImpUid(@Param("impUid") String impUid);


    // 만료된 주문 조회
    // PENDING 또는 RESERVED 상태이면서 15분 이상 지난 주문
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.item " +
            "WHERE o.status IN ('PENDING', 'RESERVED') " +
            "AND o.createdAt < :expireTime")
    List<Order> findExpiredOrders(@Param("expireTime") LocalDateTime expireTime);
}
