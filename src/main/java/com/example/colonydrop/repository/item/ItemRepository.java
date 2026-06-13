package com.example.colonydrop.repository.item;

import com.example.colonydrop.entity.item.Item;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // ✅ 추가 - 재고가 SALE 상태일 때만 SOLD로 변경 (원자적 처리)
    // 영속성 컨텍스트 캐시를 거치지 않고 DB에 직접 조건부 UPDATE
    // 동시에 여러 트랜잭션이 호출해도 DB row-lock으로 단 하나만 1을 반환
    @Modifying
    @Query("UPDATE Item i SET i.status = 'SOLD' WHERE i.id = :itemId AND i.status = 'SALE'")
    int markAsSoldIfAvailable(@Param("itemId") Long itemId);
}
