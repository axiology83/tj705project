package kr.co.tj.qnaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
=======

@SpringBootApplication
>>>>>>> b0db4c1e9951c59ddf634a5a09983b1f30746a06
public class QnaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QnaServiceApplication.class, args);
	}

}
