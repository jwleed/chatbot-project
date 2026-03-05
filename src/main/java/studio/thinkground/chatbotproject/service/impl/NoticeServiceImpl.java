package studio.thinkground.chatbotproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import studio.thinkground.chatbotproject.data.entity.NoticeEntity;
import studio.thinkground.chatbotproject.data.repository.NoticeRepository;
import studio.thinkground.chatbotproject.service.NoticeService;

import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired private NoticeRepository noticeRepository;
    @Autowired private RestTemplate restTemplate;
    @Value("${gemini.api.key}") private String apiKey;

    @Override
    @Transactional
    public void saveNotice(String title, String content, String date, String url) {

        System.out.println("받은 제목: " + title);

        NoticeEntity entity = new NoticeEntity();
        entity.setTitle(title);
        entity.setContent(content);
        entity.setDate(date);
        entity.setUrl(url);

        NoticeEntity saved = noticeRepository.save(entity);
        System.out.println("DB 저장 성공 ID: " + saved.getId()); // 저장 후 ID 확인
    }

    @Override
    public String askGemini(String userQuestion) {
        String keyword = userQuestion.substring(0,Math.min(userQuestion.length(),2));
        List<NoticeEntity> notices = noticeRepository.findByTitleContainingOrderByDateDesc(keyword);

        StringBuilder prompt = new  StringBuilder();
        prompt.append("너는 너는 대학 공지 안내 비서야. 제공된 정보로만 정확하고 친절하게 답해줘.\n\n");

        for (NoticeEntity n : notices) {
            prompt.append("[공지] ").append(n.getTitle()).append("\n내용: ").append(n.getContent()).append("\n\n");
        }

        prompt.append("질문: ").append(userQuestion);

        return callApi(prompt.toString());
    }

    private String callApi(String promptText) {
        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        Map<String, Object> body = Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", promptText)))));

        try {
            Map<String, Object> response = restTemplate.postForObject(url, body, Map.class);
            List<?> candidates = (List<?>) response.get("candidates");
            Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
            Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
            List<?> parts = (List<?>) content.get("parts");
            Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);
            return (String) firstPart.get("text");
        } catch (Exception e) {
            return "챗봇 응답 오류: " + e.getMessage();
        }
    }


}
