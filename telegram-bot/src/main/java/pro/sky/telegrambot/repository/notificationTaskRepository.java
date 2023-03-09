package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.notification_task;

import java.time.LocalDateTime;
import java.util.List;

public interface notificationTaskRepository extends JpaRepository<notification_task, Long> {
List<notification_task> getByDateTimeNotificationEquals(LocalDateTime dateTime);
}
