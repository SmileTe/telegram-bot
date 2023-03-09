package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class notification_task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String textNotification;
    private LocalDateTime dateTimeNotification;

    //public Long amount;

    public notification_task(Long chatId, String textNotification, LocalDateTime dateTimeNotification) {
        this.chatId = chatId;
        this.textNotification = textNotification;
        this.dateTimeNotification = dateTimeNotification;
       // this.id = amount;
       // amount ++;

    }

    public notification_task() {
    }

    public Long getChatId() {
        return chatId;
    }

    public String getTextNotification() {
        return textNotification;
    }

    public LocalDateTime getDateTimeNotofication() {
        return dateTimeNotification;
    }


}
