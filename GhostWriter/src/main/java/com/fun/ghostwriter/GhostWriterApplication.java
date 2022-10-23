package com.fun.ghostwriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GhostWriterApplication {

	public static void main(String[] args) {
		System.setProperty("server.port","8090");
        System.setProperty("server.tomcat.max-threads","200");
        System.setProperty("server.connection-timeout","60000");
        System.setProperty("server.so-timeout","60000");
        
		SpringApplication.run(GhostWriterApplication.class, args);


	}

}
