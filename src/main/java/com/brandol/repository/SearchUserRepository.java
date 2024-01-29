package com.brandol.repository;


import com.brandol.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchUserRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT * FROM Member  ORDER BY RAND() limit 3",nativeQuery = true)
    List<Member> findThreeByRandom();
}
