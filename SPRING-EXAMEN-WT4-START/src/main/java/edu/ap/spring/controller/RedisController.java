package edu.ap.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ap.spring.redis.RedisService;

@Controller
public class RedisController {

   private List<String> redisMessages = new ArrayList<String>();
   private RedisService service;
	
   @Autowired
	public void setRedisService(RedisService service) {
		this.service = service;
   }
     
   @RequestMapping("/messages")
   @ResponseBody
   public String messages() {
	   String html = "<HTML><HEAD><meta http-equiv=\"refresh\" content=\"5\"></HEAD>";
	   html += "<BODY><h1>Messages</h1><br/><br/><ul>";
	   for(String m : redisMessages) {
		   html += "<li>" + m;
	   }
	   html += "</BODY></HTML>";
	   
	   return html;
   }
   
   @RequestMapping("/list")
   @ResponseBody
   public String list() {

	   String html = "<HTML>";
	   // get the bitcount of our counter
	   html += "<BODY><h1>" + service.bitCount("countt") + " Inhaalexamens</h1><br/><br/><ul>";
	   
	   Set<byte[]> movies = service.keys("movies:*");
	   for(byte[] m : movies) {
		   // make a key from the byte array
		   String key = new String(m);
		   // get hash with actors
		   Map<Object, Object> actors = service.hgetAll(key);
		   // get all parts of the key, eg : ["movies", "1998", "The Big Lebowski"]
		   String[] parts = key.split(":");
		   
		   html += "<li>" + parts[2] + " (" + parts[1] + ")<br/>";
		   html += "Actors : ";
		   // iterate over actors
		   for(Entry<Object, Object> entry : actors.entrySet()) {
			   html += entry.getValue() + ", ";
		   }
		   // strip off last ', '
		   html = html.substring(0, html.length() - 2);
	   }
	   html += "</BODY></HTML>";
	   
	   return html;
   }
   
   // Messaging
   public void onMessage(String message) {
	   this.redisMessages.add(message);
   }
}
