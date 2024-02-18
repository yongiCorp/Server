package com.brandol.repository;

import com.brandol.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query(value = "select m from Member m WHERE m.id = :id")
    Member findOneById(@Param("id") Long id);

    @Query(value = "SELECT * FROM member WHERE member.role = 'ROLE_USER' ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Member> findThreeByRandom();

    @Query(value = "SELECT * FROM member  WHERE member.role = 'ROLE_USER' ORDER BY RAND() ",nativeQuery = true)
    List<Member> findAllByRandom();


    Optional<Member> findByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<Member> findById(Long id);

    List<Member> findByNameContaining(String searchContetns);
}