package studio.thinkground.chatbotproject.service;

public interface NoticeService {
    public void saveNotice(String title, String content, String date, String url);

    public String askGemini(String userQuestion);



}
