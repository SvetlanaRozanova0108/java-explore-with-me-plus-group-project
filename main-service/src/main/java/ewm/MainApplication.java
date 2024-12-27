package ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.practicum.ewm.stats.server.StatsApp;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatsApp.class, args);
    }
}
