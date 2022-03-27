package com.zensar.olx.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.olx.DB.OlxUserDAO;
import com.zensar.olx.bean.OlxUser;

@Service
public class OlxUserService {

	@Autowired
	OlxUserDAO dao;
	
	public OlxUser addOlxUse(OlxUser olxUser)
	{
		return this.dao.save(olxUser);
	}
	public OlxUser updateOlxUser(OlxUser olxUser)
	{
		return this.dao.save(olxUser);//when record not found add new entity otherwise update
	}
	
	public OlxUser findOlxUser(int id)
	{
		Optional<OlxUser> optional=this.dao.findById(id);
		if(optional.isPresent())
			return optional.get();
		else
			return null;
	}
	
	public OlxUser findOlxUserByName(String name)
	{
		OlxUser olxUser=this.dao.findByUserName(name);
		return olxUser;
	}
}
