package studio.thinkground.chatbotproject.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studio.thinkground.chatbotproject.data.entity.NoticeEntity;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findByTitleContainingOrderByDateDesc(String keyword);
    boolean existsByTitleAndDate(String title, String date);
}
