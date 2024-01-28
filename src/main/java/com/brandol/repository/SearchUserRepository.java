package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchUserRepository extends JpaRepository<Member, Long> {
}
