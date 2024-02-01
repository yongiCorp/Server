package com.brandol.repository;

import com.brandol.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Long> {
}
