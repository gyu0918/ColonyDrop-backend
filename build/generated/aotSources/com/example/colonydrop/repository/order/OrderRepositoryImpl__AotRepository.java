package com.example.colonydrop.repository.order;

import com.example.colonydrop.entity.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.query.Param;

/**
 * AOT generated JPA repository implementation for {@link OrderRepository}.
 */
@Generated
public class OrderRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public OrderRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link OrderRepository#findByBuyerMemberId(java.lang.String)}.
   */
  public List<Order> findByBuyerMemberId(@Param("memberId") String memberId) {
    String queryString = "SELECT o FROM Order o JOIN FETCH o.item WHERE o.buyer.memberId = :memberId ORDER BY o.createdAt DESC";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("memberId", memberId);

    return (List<Order>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link OrderRepository#findByImpUid(java.lang.String)}.
   */
  public Optional<Order> findByImpUid(@Param("impUid") String impUid) {
    String queryString = "SELECT o FROM Order o JOIN FETCH o.item JOIN FETCH o.buyer WHERE o.impUid = :impUid";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("impUid", impUid);

    return Optional.ofNullable((Order) convertOne(query.getSingleResultOrNull(), false, Order.class));
  }

  /**
   * AOT generated implementation of {@link OrderRepository#findByMerchantUid(java.lang.String)}.
   */
  public Optional<Order> findByMerchantUid(@Param("merchantUid") String merchantUid) {
    String queryString = "SELECT o FROM Order o JOIN FETCH o.item JOIN FETCH o.buyer WHERE o.merchantUid = :merchantUid";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("merchantUid", merchantUid);

    return Optional.ofNullable((Order) convertOne(query.getSingleResultOrNull(), false, Order.class));
  }

  /**
   * AOT generated implementation of {@link OrderRepository#findByMerchantUidAndBuyerMemberId(java.lang.String,java.lang.String)}.
   */
  public Optional<Order> findByMerchantUidAndBuyerMemberId(@Param("merchantUid") String merchantUid,
      @Param("memberId") String memberId) {
    String queryString = "SELECT o FROM Order o JOIN FETCH o.item JOIN FETCH o.buyer WHERE o.merchantUid = :merchantUid AND o.buyer.memberId = :memberId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("merchantUid", merchantUid);
    query.setParameter("memberId", memberId);

    return Optional.ofNullable((Order) convertOne(query.getSingleResultOrNull(), false, Order.class));
  }

  /**
   * AOT generated implementation of {@link OrderRepository#findExpiredOrders(java.time.LocalDateTime)}.
   */
  public List<Order> findExpiredOrders(@Param("expireTime") LocalDateTime expireTime) {
    String queryString = "SELECT o FROM Order o JOIN FETCH o.item WHERE o.status IN ('PENDING', 'RESERVED') AND o.createdAt < :expireTime";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("expireTime", expireTime);

    return (List<Order>) query.getResultList();
  }
}
