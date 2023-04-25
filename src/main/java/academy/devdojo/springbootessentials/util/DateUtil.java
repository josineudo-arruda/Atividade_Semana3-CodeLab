package academy.devdojo.springbootessentials.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// @Repository
@Component //injeção de independencia na classe
public class DateUtil {
    public String formatLocalTimeToDatabaseStyle(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-aa HH:mm:ss").format(localDateTime);
    }
}
