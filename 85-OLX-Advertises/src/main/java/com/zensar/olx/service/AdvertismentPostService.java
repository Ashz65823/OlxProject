package com.zensar.olx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.olx.DB.AdvertismentPostDAO;
import com.zensar.olx.bean.AdvertisementPost;

@Service
public class AdvertismentPostService {

	@Autowired
	AdvertismentPostDAO dao;
	
	
	public AdvertisementPost addAdvertisement (AdvertisementPost advertisementPost)
	{
		return this.dao.save(advertisementPost);
	}
	
	public AdvertisementPost updateAdvertisement(AdvertisementPost advertisementPost)
	{
		return this.dao.save(advertisementPost);
	}
	
	public List<AdvertisementPost> getAllAdvertisement()
	{
		return this.dao.findAll();
	}
	
	
	public boolean deleteAdvertisement(AdvertisementPost advertisementPost)
	{
		boolean result =false;
		try {
			this.dao.delete(advertisementPost);
			result= true;
		} catch (Exception e) {
			e.printStackTrace();
			result =false;
		}
		return result;
	}
	
	public AdvertisementPost getAdvertisementById(int id)
	{
		Optional<AdvertisementPost> optional;
		optional=dao.findById(id);
		if(optional.isPresent())
			return optional.get();
		else
			return null;
	}
	/*
	 * public AdvertisementPost findElementByUserName(String userName,int id) {
	 * Optional<AdvertisementPost> optional=dao.findById(id);
	 * if(optional.isPresent()) return optional.get(); else return null; }
	 */
}
