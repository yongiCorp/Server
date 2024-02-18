package com.brandol.repository;

import com.brandol.domain.ChatRooms;
import com.brandol.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomsRepository extends JpaRepository<ChatRooms,Long> {

    List<ChatRooms> findChatRoomsByUserAAndUserB(Member userA,Member userB);

    @Query("select cr from ChatRooms cr where cr.userA.id = :memberId")
    List<ChatRooms> findChatRoomsByUserAId(@Param("memberId")Long memberId);

    @Query("select cr from ChatRooms cr where cr.userB.id = :memberId")
    List<ChatRooms> findChatRoomsByUserBId(@Param("memberId")Long memberId);
}
