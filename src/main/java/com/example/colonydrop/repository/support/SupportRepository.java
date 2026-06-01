package com.example.colonydrop.repository.support;

import com.example.colonydrop.entity.support.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {

    // 전체 문의 조회 (최신순) - 관리자용
    @Query("SELECT s FROM Support s JOIN FETCH s.author ORDER BY s.createdAt DESC")
    List<Support> findAllWithAuthor();

    // 내 문의 조회 (최신순) - 유저용
    @Query("SELECT s FROM Support s WHERE s.author.memberId = :memberId ORDER BY s.createdAt DESC")
    List<Support> findByAuthorMemberId(@Param("memberId") String memberId);

    // 문의 상세 조회
    @Query("SELECT s FROM Support s JOIN FETCH s.author WHERE s.id = :id")
    Optional<Support> findByIdWithAuthor(@Param("id") Long id);
}