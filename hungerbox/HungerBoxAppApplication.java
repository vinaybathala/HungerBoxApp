package com.hungerbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hungerbox.config.RibbonConfiguration;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableScheduling
@RibbonClient(value = "order-loadbalance", configuration = RibbonConfiguration.class)
public class HungerBoxAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HungerBoxAppApplication.class, args);
	}

}
