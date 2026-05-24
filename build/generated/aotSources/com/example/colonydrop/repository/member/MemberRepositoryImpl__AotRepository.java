package com.example.colonydrop.repository.member;

import com.example.colonydrop.entity.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link MemberRepository}.
 */
@Generated
public class MemberRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public MemberRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link MemberRepository#findByMemberId(java.lang.String)}.
   */
  public Member findByMemberId(String memberId) {
    String queryString = "SELECT m FROM Member m WHERE m.memberId = :memberId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("memberId", memberId);

    return (Member) convertOne(query.getSingleResultOrNull(), false, Member.class);
  }
}
