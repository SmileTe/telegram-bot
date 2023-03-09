package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pro.sky.telegrambot.model.notification_task;
import pro.sky.telegrambot.repository.notificationTaskRepository;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private notificationTaskRepository notificationTaskRepository;

     @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            if(update.message().text().equals("/start")){
               SendMessage message = new SendMessage(update.message().chat().id(), "Приветствую!");
               SendResponse response = telegramBot.execute(message);
            }
            else
            {
                String message = update.message().text();
                Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
                Matcher matcher = pattern.matcher(message);
                if (matcher.matches()) {
                    // обрабатываем ситуацию, когда строка соответствует паттерну
                    String date = matcher.group(1);
                    String item = matcher.group(3);
                    LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                    //сконструировать сущность
                    notification_task task = new notification_task(update.message().chat().id(),item,dateTime);
                   notificationTaskRepository.save(task);
                }
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(fixedRate = 60000)
    public void getByDateTimeNotificationLike() {
         LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
           Collection<notification_task> notification_tasks = notificationTaskRepository.getByDateTimeNotificationEquals(dateTime);
        for (notification_task task:
                notification_tasks ) {
            SendMessage message = new SendMessage(task.getChatId(), task.getTextNotification());
            SendResponse response = telegramBot.execute(message);
        }

    }



}
