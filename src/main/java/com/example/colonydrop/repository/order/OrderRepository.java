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
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.item " +
            "WHERE o.status IN ('PENDING', 'RESERVED') " +
            "AND o.createdAt < :expireTime")
    List<Order> findExpiredOrders(@Param("expireTime") LocalDateTime expireTime);

    // 나의 주문 조회 (최신순)
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.item " +
            "WHERE o.buyer.memberId = :memberId " +
            "ORDER BY o.createdAt DESC")
    List<Order> findByBuyerMemberId(@Param("memberId") String memberId);

    // 주문 상세 조회 (merchantUid + 본인 확인)
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.item " +
            "JOIN FETCH o.buyer " +
            "WHERE o.merchantUid = :merchantUid " +
            "AND o.buyer.memberId = :memberId")
    Optional<Order> findByMerchantUidAndBuyerMemberId(
            @Param("merchantUid") String merchantUid,
            @Param("memberId") String memberId);

    // ✅ 관리자용 전체 주문 조회 (최신순)
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.item " +
            "JOIN FETCH o.buyer " +
            "ORDER BY o.createdAt DESC")
    List<Order> findAllWithItemAndBuyer();

    //한아이디당 한개의 제품만 구매하도록
    @Query("SELECT COUNT(o) > 0 FROM Order o " +
            "WHERE o.buyer.memberId = :memberId " +
            "AND o.status IN ('PENDING', 'RESERVED')")
    boolean existsActiveOrderByMemberId(@Param("memberId") String memberId);
}