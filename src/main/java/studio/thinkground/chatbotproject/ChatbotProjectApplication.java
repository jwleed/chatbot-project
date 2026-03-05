package studio.thinkground.chatbotproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ChatbotProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotProjectApplication.class, args);


    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
