package ru.spshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.spshop.config.RsaKeyConfigProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class AuthServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApp.class, args);
	}

}
