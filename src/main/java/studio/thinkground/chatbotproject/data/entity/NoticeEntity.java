package studio.thinkground.chatbotproject.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notices")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private String date;
    private String url;


    public NoticeEntity(String title, String content, String date, String url) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.url = url;
    }
}
