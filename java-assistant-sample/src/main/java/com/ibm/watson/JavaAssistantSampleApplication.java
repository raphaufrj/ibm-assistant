package com.ibm.watson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaAssistantSampleApplication {

	public static void main(String[] args) {
		
		SpringApplication app =
                new SpringApplication(JavaAssistantSampleApplication.class);
/*
      Properties properties = new Properties();
      properties.setProperty("spring.resources.static-locations",
                        "classpath:/public, classpath:/static");
      app.setDefaultProperties(properties);*/
      app.run(args);
	}

}
