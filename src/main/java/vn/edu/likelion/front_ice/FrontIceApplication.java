package vn.edu.likelion.front_ice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class FrontIceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontIceApplication.class, args);
	}

}
