package com.project.fri.chatting.controller;

import com.project.fri.chatting.dto.CreateSocketChattingMessageRequest;
import com.project.fri.chatting.dto.CreateChattingMessageRequest;
import com.project.fri.chatting.dto.FindChattingMessageResponse;
import com.project.fri.chatting.service.ChattingServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
@Slf4j
public class ChattingController {
  private final SimpMessageSendingOperations messagingTemplate;
  private final ChattingServiceImpl chattingService;

  // pub, sub관리 컨트롤러 RequestMapping 무시..
  @MessageMapping("/message")
  public void message(CreateSocketChattingMessageRequest chatMessage){
    System.out.println("클라이언트에서 pub이벤트 발생!!");
    messagingTemplate.convertAndSend("/sub/room/" + chatMessage.getRoomId(), chatMessage);
  }

  @PostMapping()
  public ResponseEntity<?> createChatting(@RequestBody CreateChattingMessageRequest request,@RequestHeader Long userId){
    chattingService.createChatting(request,userId);
    return ResponseEntity.ok("ok");
  }


  @GetMapping("/{roomId}")
  public ResponseEntity<List<FindChattingMessageResponse>> findChattingList(@PathVariable Long roomId){
    List<FindChattingMessageResponse> responseList = chattingService.findChatting(roomId);
    return ResponseEntity.ok(responseList);
  }
}