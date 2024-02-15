package com.brandol.repository;

import com.brandol.domain.ChatRooms;
import com.brandol.dto.ChatRoomDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {

    Optional<ChatRooms> findById(Long id);

    //ChatRooms findAllById(Long Id);

    /*
    ChatRoom findByRecruitmentId(Integer recruitmentId);

    // 채팅방 정보 조회
    @Query(value = "SELECT COUNT(*) + 1 AS count, c.crew_name, r.title " +
            " FROM crewpass.chat_room cr" +
            " INNER JOIN crewpass.user_chat_room ucr ON ucr.chat_room_chat_room_id = cr.chat_room_id" +
            " INNER JOIN crewpass.recruitment r ON cr.recruitment_recruitment_id = r.recruitment_id" +
            " INNER JOIN crewpass.crew c ON r.crew_crew_id = c.crew_id" +
            " WHERE cr.is_deleted = 0" +
            " GROUP BY cr.chat_room_id" +
            " HAVING cr.chat_room_id = :chatroomId", nativeQuery = true)
    ChatRoomInfo findInfoByChatroomId(@Param("chatroomId") Integer chatroomId);

    // 동아리 - 채팅방 리스트 조회
    @Query(value = "SELECT cr.chat_room_id, c.crew_id, c.crew_name, crew_profile, r.title " +
            "  FROM crewpass.chat_room cr " +
            "   INNER JOIN crewpass.recruitment r ON r.recruitment_id = cr.recruitment_recruitment_id " +
            "   INNER JOIN crewpass.crew c ON c.crew_id = r.crew_crew_id " +
            "   INNER JOIN crewpass.crew_chat_room ccr ON ccr.chat_room_chat_room_id = cr.chat_room_id " +
            "  WHERE ccr.crew_crew_id = :crewId and cr.is_deleted = 0", nativeQuery = true)
    List<ChatRoomList> findChatRoomListByCrewId(@Param("crewId") Integer crewId);
*/
    /*
    // 회원 - 채팅방 리스트 조회
    @Query(value = "SELECT cr.chatrooms_id, c.crew_id, c.crew_name, crew_profile, r.title " +
            "  FROM chat_rooms cr " +
            "   INNER JOIN crewpass.crew c ON c.crew_id = r.crew_crew_id " +
            "   INNER JOIN crewpass.user_chat_room ucr ON ucr.chat_room_chat_room_id = cr.chat_room_id " +
            "  WHERE ucr.user_user_id = :userId and cr.is_deleted = 0", nativeQuery = true)
    List<ChatRoomDto> findChatRoomListByUserId(@Param("memberId") Long memberId);


     */
    /*
    // 채팅방 삭제
    @Modifying
    @Transactional
    @Query(value = "UPDATE crewpass.chat_room SET is_deleted = 1 WHERE close_time < now()", nativeQuery = true)
    void deleteChatRoom();

    // 일시적으로 Safe Update 해제
    @Modifying
    @Transactional
    @Query(value = "set sql_safe_updates=0", nativeQuery = true)
    void disabledSafeUpdates();

     */
}
