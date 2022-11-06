package com.fun.ghostwriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GhostWriterApplication {

	public static void main(String[] args) {
		  System.setProperty("server.port","8090");
//        System.setProperty("server.tomcat.max-threads","200");
//        System.setProperty("server.connection-timeout","60000");
        
		SpringApplication.run(GhostWriterApplication.class, args);


	}
	
//
//	@Bean
//	public RestTemplate getRestTemplate() {
//		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//		clientHttpRequestFactory.setConnectTimeout(500000);
//		clientHttpRequestFactory.setReadTimeout(500000);
//		return new RestTemplate(clientHttpRequestFactory);
//	}

}
