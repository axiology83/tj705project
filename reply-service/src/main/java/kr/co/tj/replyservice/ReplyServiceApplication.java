package kr.co.tj.replyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReplyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReplyServiceApplication.class, args);
	}

}
