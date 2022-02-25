package com.zensar.olx.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.olx.bean.OlxUser;
import com.zensar.olx.service.OlxUserService;

@RestController
public class OlxUserController {

	@Autowired
	OlxUserService service;
	
	@PostMapping("/user")
	public OlxUser addOlxUser(@RequestBody OlxUser olxUser)//this is request to post
	{
		return this.service.addOlxUse(olxUser);
	}
	
	@GetMapping("/user/{uid}")
	public OlxUser findOlxUserById(@PathVariable(name="uid") int id)//this is to get value from uid
	{
		return this.service.findOlxUser(id);
	}
	
	@GetMapping("/user/find/{userName}")
	public OlxUser finOlxUserByName(@PathVariable(name="userName") String name)
	{
		return this.service.findOlxUserByName(name);
	}
}
