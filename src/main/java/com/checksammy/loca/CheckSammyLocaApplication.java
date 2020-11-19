package com.checksammy.loca;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * 
 * @author kartik.thakre
 *
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class CheckSammyLocaApplication  extends SpringBootServletInitializer {
	
	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CheckSammyLocaApplication.class);
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(CheckSammyLocaApplication.class, args);
	}

}
