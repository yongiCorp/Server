package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.request.ChatMessageRequestDto;
import com.brandol.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "채팅 관련 API", description = "메시지 전송, 조회 기능 담당")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat/room")
    public ApiResponse<String> createChatRoom(@RequestParam Long sender, @RequestParam Long receiver){
       Long roomId = chatService.makeChatRoom(sender,receiver);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(),"chatRoomId: "+roomId);
    }

    @PostMapping("/chat/room/{roomId}")
    public ApiResponse<String> sendMessage(@RequestBody ChatMessageRequestDto.sendMessage dto, @PathVariable("roomId")Long roomId, @RequestParam("senderId")Long senderId){
        Long messageId = chatService.sendMessage(dto,roomId,senderId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(),"messageId: " +messageId);
    }
}
