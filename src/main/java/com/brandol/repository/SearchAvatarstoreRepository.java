package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchAvatarstoreRepository extends JpaRepository<Items, Long> {
}
