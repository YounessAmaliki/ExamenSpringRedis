package edu.ap.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import edu.ap.spring.controller.RedisController;
import edu.ap.spring.redis.RedisService;

@SpringBootApplication
public class RedisApplication {
	
	private RedisService service;
	private String CHANNEL = "edu:ap:redis";
	private String KEY = "edu:ap:test";
	
	@Autowired
	public void setRedisService(RedisService service) {
		this.service = service;
	}
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
											MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new ChannelTopic(CHANNEL));

		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(RedisController controller) {
		return new MessageListenerAdapter(controller, "onMessage");
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return (args) -> {
			// Messaging
	 		service.sendMessage(CHANNEL, "Hello from Spring Boot");
	 		
	 		// Keys
	 		service.setKey(KEY, "Key from Spring Boot");
	 		System.out.println(service.getKey(KEY));
	 		
	 		// Bitops
	 		String bitKey = "edu:ap:bits";
	 		service.setBit(bitKey, 73, true);
	 		service.setBit(bitKey, 85, true);
	 		service.setBit(bitKey, 90, true);
	 		System.out.println(bitKey + ", bit 73 : " + service.getBit(bitKey, 73));
	 		System.out.println(bitKey + ", bit count : " + service.bitCount(bitKey));
	 		
	 		// Movies
	 		Map<String, String> actors = new HashMap<String, String>();
	 		actors.put("actor1", "Jeff Bridges");
	 		actors.put("actor2", "John Goodman");
	 		service.hset("movies:1998:The Big Lebowski", actors);
	 		service.setBit("moviescount", 0, true);
	 		actors.clear();
	 		actors.put("actor1", "Billy Bob Thornton");
	 		actors.put("actor2", "Tony Cox");
	 		service.hset("movies:2003:Bad Santa", actors);
	 		service.setBit("moviescount", 1, true);
		};
    }
	
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
}
