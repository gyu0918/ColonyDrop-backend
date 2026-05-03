package com.example.colonydrop.repository.order;

import com.example.colonydrop.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByMerchantUid(String merchantUid);
    Optional<Order> findByImpUid(String impUid);
}
