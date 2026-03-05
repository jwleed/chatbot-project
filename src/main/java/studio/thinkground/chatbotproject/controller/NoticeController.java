package studio.thinkground.chatbotproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studio.thinkground.chatbotproject.data.dto.NoticeRequestDto;
import studio.thinkground.chatbotproject.data.entity.NoticeEntity;
import studio.thinkground.chatbotproject.service.NoticeService;

@RestController
@RequestMapping("/api/chatbot")
public class NoticeController {

    @Autowired
    private NoticeService service;

    // 크롤러가 데이터 저장할 때 사용
    @PostMapping("/save")
    public ResponseEntity<String> saveNotice(@RequestBody NoticeRequestDto dto) {

        System.out.println("파이썬이 보낸 데이터: " + dto.getTitle());

        service.saveNotice(dto.getTitle(), dto.getContent(), dto.getDate(), dto.getUrl());
        return ResponseEntity.ok("성공");
    }



    // 질문할 때 사용
    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return service.askGemini(question);
    }
}
