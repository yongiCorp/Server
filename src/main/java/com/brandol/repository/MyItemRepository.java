package com.brandol.repository;

import com.brandol.domain.mapping.MyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyItemRepository extends JpaRepository<MyItem, Long> {

    List<MyItem> findALlByMemberId(Long memberId);
    List<MyItem> findALlByMemberIdAndIsWearing(Long memberId, Boolean isWearing);
    Optional<MyItem> findByItemsIdAndMemberId(Long itemId, Long memberId);
}
