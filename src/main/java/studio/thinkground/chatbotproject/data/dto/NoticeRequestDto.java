package studio.thinkground.chatbotproject.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NoticeRequestDto {

    private String title;
    private String content;
    private String date;
    private String url;
}
