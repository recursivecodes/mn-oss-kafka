package mn.oss.kafka;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Async;

import java.util.UUID;

public class Application {

    MessageProducer messageProducer;

    public Application(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = Micronaut.run(Application.class);
    }

    @EventListener
    @Async
    public void onStartup(StartupEvent event) {
        for(int i=0; i<10; i++) {
            String key = UUID.randomUUID().toString();
            String val = "Message " + i + " from Micronaut!";
            messageProducer.sendMessage(key, val);
            System.out.println("Sent message #" + i + " to topic.");
        }
        System.out.println("All messages sent to consumer");

    }
}