package com.hertz;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.hertz.cache.LRUCache;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
	
	@Bean
	@Qualifier("CatCache")
    public LRUCache createCatCache(){
		return new LRUCache<>();
	}
	
	@Bean
	@Qualifier("PrdCache")
    public LRUCache createPrdCache(){
		return new LRUCache<>();
		
	}
}
