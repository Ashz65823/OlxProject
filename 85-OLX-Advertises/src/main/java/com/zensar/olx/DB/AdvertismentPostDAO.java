package com.zensar.olx.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zensar.olx.bean.AdvertisementPost;

@Repository
public interface AdvertismentPostDAO extends JpaRepository<AdvertisementPost, Integer> {
	
}
