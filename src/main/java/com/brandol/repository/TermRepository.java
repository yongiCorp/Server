package com.brandol.repository;

import com.brandol.domain.Term;
import com.brandol.domain.enums.TermType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {
    List<Term> findByTermType(TermType termType);
}
