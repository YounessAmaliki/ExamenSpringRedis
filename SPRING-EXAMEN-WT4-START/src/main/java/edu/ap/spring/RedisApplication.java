package edu.ap.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import edu.ap.spring.model.InhaalExamen;
import edu.ap.spring.model.InhaalExamenRepository;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import edu.ap.spring.controller.RedisController;
import edu.ap.spring.redis.RedisService;


@SpringBootApplication
public class RedisApplication {

	@Autowired
	private InhaalExamen inhaalexamen;
	
	@Autowired
	private InhaalExamenRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return (args) -> {
	 		InhaalExamen inhaalexamen1 = new InhaalExamen("Jones", "Webtech", "Ziek");
	 		repository.save(inhaalexamen1);
	 		
	 		
		};
    }

    
}
