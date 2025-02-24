package ru.spshop;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GateWay {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
            System.out.println("Loaded: " + entry.getKey() + " = " + entry.getValue());
        });
        System.out.println("SERVER_PORT from System.getProperty(): " + System.getProperty("SERVER_PORT"));

        SpringApplication.run(GateWay.class, args);
    }

}