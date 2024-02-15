package com.brandol.controller;

import com.brandol.service.ChatRoomService;
import com.brandol.service.UserChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserChatRoomController {
    /*
    private final UserChatRoomService userChatRoomService;
    private final ChatRoomService chatRoomService;

    // 생성자로 DI 주입
    @Autowired
    public UserChatRoomController(UserChatRoomService userChatRoomService, ChatRoomService chatRoomService) {
        this.userChatRoomService = userChatRoomService;
        this.chatRoomService = chatRoomService;
    }

    // 채팅방 가입
    @PostMapping("/chat/new/user/{chatroomId}")
    public ResponseEntity registerUserChatRoom (
            @PathVariable("chatroomId") Integer chatroomId,
            @RequestHeader("userId") Integer userId
    ) throws IOException {
        // 해당 채팅방의 몇 번째 회원인지 구하기
        Integer enterOrder = userChatRoomService.findLastEnterOrderByChatRoomId(chatroomId) + 1;

        UserChatRoom userChatRoom = new UserChatRoom(null, userId, chatroomId, null, enterOrder); // 처음 채팅방 가입 시에는 읽은 게 없으므로 null로 표시
        Integer userChatRoomId = userChatRoomService.registerUserChatRoom(userChatRoom);

        if (userChatRoomId != null) {
            UserChatRoomId responseId = new UserChatRoomId(userChatRoomId);
            return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.REGISTER_SUCCESS_USER_CHAT_ROOM, responseId), HttpStatus.OK);
        } else {
            return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.FAIL, ResponseMessage.REGISTER_FAIL_USER_CHAT_ROOM, null), HttpStatus.OK);
        }
    }

    // 회원 - 채팅방 리스트 조회
    @GetMapping("/chat/user/myList")
    public ResponseEntity checkUserChatRoomList (
            @RequestHeader("userId") Integer userId
    ) throws IOException {
        return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.READ_USER_CHATROOM_LIST, chatRoomService.findChatRoomListByUserId(userId)), HttpStatus.OK);
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
