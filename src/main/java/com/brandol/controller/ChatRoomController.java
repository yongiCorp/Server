package com.brandol.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Timestamp;
import java.util.Calendar;

import static com.amazonaws.util.AWSRequestMetrics.Field.StatusCode;

@RestController
@RequiredArgsConstructor
@Tag(name = "채팅방 관련 API", description = "")
public class ChatRoomController {
    /*
    // 채팅방 생성
    @PostMapping("/chat/new/{recruitmentId}")
    public ResponseEntity createChatRoom (
            @PathVariable("recruitmentId") Integer recruitmentId,
            @RequestHeader("crewId") Integer crewId
    ) throws IOException {

        Recruitment recruitment = recruitmentService.findByRecruitmentId(recruitmentId);

        Timestamp closeTime = recruitment.getDeadline();
        System.out.println("old ts : " + closeTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(closeTime);
        cal.add(Calendar.MONTH, 1); // 한달 이후에 채팅방이 폐쇄되도록 설정
        closeTime.setTime(cal.getTime().getTime());
        System.out.println("new ts : " + closeTime);

        if (crewId.equals(recruitment.getCrewId())) {
            ChatRoom chatRoom = new ChatRoom(null, recruitmentId,
                    closeTime, 0);
            Integer chatRoomId = chatRoomService.createChatRoom(chatRoom);

            if (chatRoomId != null) {
                ChatRoomId responseId = new ChatRoomId(chatRoomId);
                return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.CREATED_SUCCESS_CHAT_ROOM, responseId), HttpStatus.OK);
            } else {
                return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.FAIL, ResponseMessage.CREATED_FAIL_CHAT_ROOM, null), HttpStatus.OK);
            }
        }
        return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.FAIL, ResponseMessage.CREATED_FAIL_CHAT_ROOM, null), HttpStatus.OK);
    }

    // 채팅방 정보 조회
    @GetMapping("/chat/info/{chatroomId}")
    public ResponseEntity checkChatRoomInfo (
            @PathVariable("chatroomId") Integer chatroomId
    ) throws IOException {
        return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.READ_CHATROOM_INFO, chatRoomService.findInfoByChatroomId(chatroomId)), HttpStatus.OK);
    }

    // 회원 - 해당 채팅방에서 안 읽은 채팅 개수 조회
    @GetMapping("/chat/count/{chatRoomId}/user")
    public ResponseEntity checkUserUnReadChatCount (
            @PathVariable("chatRoomId") Integer chatroomId,
            @RequestHeader("userId") Integer userId
    ) throws IOException {
        Integer count = crewChatRoomService.findUnReadChatCountByUserId(chatroomId, userId);
        UnReadCount response = new UnReadCount(count);
        return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.CHECK_USER_UNREAD_CHAT_COUNT, response), HttpStatus.OK);
    }

     */
}
