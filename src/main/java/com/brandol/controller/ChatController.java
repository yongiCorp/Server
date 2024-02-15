package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.converter.ChatConverter;
import com.brandol.domain.ChatMessages;
import com.brandol.dto.ChatDto;
import com.brandol.dto.response.SearchResponseDto;
import com.brandol.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.amazonaws.util.AWSRequestMetrics.Field.StatusCode;

@RestController
@RequiredArgsConstructor
@Tag(name = "채팅 관련 API", description = "")
@Validated
public class ChatController {

    SimpMessagingTemplate messagingTemplate;
    ChatService chatService;

    // 채팅 메시지 송신
    // /pub/chat/message 엔드포인트로 들어오는 WebSocket 메시지를 처리
    @Operation(summary = "채팅 메시지 송신",description ="" )
    @MessageMapping("/chat/message")
    public ApiResponse<Long> chat(@RequestBody ChatDto.ChattingDto chattingDto) {
        Long a = chatService.createChatMessage(chattingDto);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), a);
    }

    /*
    public ResponseEntity sendChatMessage(
            @RequestBody ChatDto.ChattingDto chattingDto // 이 안에 crewId, userId를 담아서 같이 보냄!
            // 회원이 보낸 경우, "crewId" : null로 보내기
            // 동아리가 보낸 경우, "userId" : null로 보내기
    ) {



        // 채팅을 보낸 동아리/회원의 last_read_chat_id 갱신
        if (chatDto.getCrewId() != null && chatDto.getUserId() == null) { // 동아리가 보낸 경우
            updateCrewLastReadChatId(chatDto.getChatRoomId(), chatDto.getCrewId());
        } else { // 회원이 보낸 경우
            updateUserLastReadChatId(chatDto.getChatRoomId(), chatDto.getUserId());
        }

        return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.SEND_SUCCESS_CHAT_MESSAGE, chat.getChatRoomId()), HttpStatus.OK);
    }
    */

    // 채팅 메시지 내역 조회
    /*
    @GetMapping("/chat/history/{chatRoomId}")
    public ResponseEntity getChatHistory(
            @PathVariable("chatRoomId") Integer chatRoomId
    ) {
        return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.SEND_SUCCESS_CHAT_HISTORY, chatService.getChatHistory(chatRoomId)), HttpStatus.OK);
    }

     */

    @Operation(summary = "채팅 메시지 내역 조회",description ="" )
    @GetMapping("/chat/history/{chatrooms_id}")
    public ApiResponse<List<ChatMessages>> chat(@PathVariable("chatrooms_id") Long id) {
        System.out.println(id+"ppspfpkjefogpargqerqrgaergergaerg");
        //Long memberId = Long.parseLong(authentication.getName());
        List<ChatMessages> b = chatService.getChatHistory(id);
        if (b == null) {
            // Null 경우를 처리합니다. 예를 들어, 빈 리스트를 반환하거나 특정 에러 응답을 반환할 수 있습니다.
            System.out.println("chatrooms_id에 대한 채팅 기록이 null입니다: " + id);
            //return ApiResponse.onError(...); // 에러 처리 방식에 맞게 조정하세요.
        }
        System.out.println(b+"!!!!!!!!!!!!!!!");
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), b);
    }

    /*
    // 동아리 - last_read_chat_id 갱신
    @PutMapping("/chat/history/{chatRoomId}/crew")
    public ResponseEntity updateCrewLastReadChatId(
            @PathVariable("chatRoomId") Integer chatRoomId,
            @RequestHeader("crewId") Integer crewId
    ) {
        Integer crewChatRoomId = crewChatRoomService.findCrewChatRoomIdByChatRoomIdAndCrewId(chatRoomId, crewId);
        Integer lastReadChatId = chatService.getLastReadChatId(chatRoomId); // 그 채팅방의 제일 마지막 last_read_chat_id
        crewChatRoomService.updateCrewLastReadChatId(crewChatRoomId, crewId, lastReadChatId);
        return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.UPDATE_CREW_LAST_READ_CHAT_ID, null), HttpStatus.OK);
    }
    */

    /*
     //회원 - last_read_chat_id 갱신
    @PutMapping("/chat/history/{chatRoomId}/user")
    public ResponseEntity updateUserLastReadChatId(
            @PathVariable("chatRoomId") Integer chatRoomId,
            @RequestHeader("userId") Integer userId
    ) {
        Integer userChatRoomId = userChatRoomService.findUserChatRoomIdByChatRoomIdAndUserId(chatRoomId, userId);
        Integer lastReadChatId = chatService.getLastReadChatId(chatRoomId); // 그 채팅방의 제일 마지막 last_read_chat_id
        userChatRoomService.updateUserLastReadChatId(userChatRoomId, userId, lastReadChatId);
        return new ResponseEntity(ResponseFormat.responseFormat(StatusCode.SUCCESS, ResponseMessage.UPDATE_USER_LAST_READ_CHAT_ID, null), HttpStatus.OK);
    }

     */




}
